package com.pos.pos.ejb;

import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductPhotoDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import com.pos.pos.entities.ProductPhoto;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ProductsBean {
    private static final Logger LOG = Logger.getLogger(ProductsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;


    public List<ProductDto> findAllProducts(){
        LOG.info("findAllProducts");
        try{
            TypedQuery<Product> typedQuery = entityManager.createQuery("SELECT p FROM Product p", Product.class);
            List<Product> products = typedQuery.getResultList();
            return copyProductsToDto(products);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    public Long findProductIdByProductName(String name) {
        List <Long> productIds = entityManager.createQuery("SELECT p.id FROM Product p WHERE p.name LIKE :inputName",Long.class)
                .setParameter("inputName",name)
                .getResultList();

        Long productId = productIds.get(0);
        return productId;
    }


    private List<ProductDto> copyProductsToDto(List<Product> products){
        List<ProductDto> productsDto = new ArrayList<>();
        ProductDto var;
        Category category;
        for(Product elem : products){
            category = elem.getCategory();
            var = new ProductDto(elem.getId(), elem.getName(), elem.getQuantity(), category);
            productsDto.add(var);
        }
        return productsDto;
    }

    public ProductsByCategoryDto findAllProductsByCategoryId(Long categoryId){
        LOG.info("findAllProductsByCategoryId");
        try{
            TypedQuery<Product> typedQuery = entityManager.createQuery("SELECT p FROM Product p", Product.class);
            List<Product> products = typedQuery.getResultList();
            return copyProductsToProductsByCategoryDto(products, categoryId);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private ProductsByCategoryDto copyProductsToProductsByCategoryDto(List<Product> products, Long categoryId){
        ProductDto productDto;
        List<ProductDto> ProductDtoList = new ArrayList<>();
        Category category = null;
        for(Product elem : products){
            if (elem.getCategory().getId() == categoryId){
                productDto = new ProductDto(elem.getId(), elem.getName(), elem.getQuantity(), elem.getCategory());
                category = elem.getCategory();
                ProductDtoList.add(productDto);

            }
        }
        if (category != null){
            ProductsByCategoryDto productsByCategoryDto = new ProductsByCategoryDto(categoryId,category.getName(),ProductDtoList);
            return productsByCategoryDto;
        }
        return null;
    }



    public void createProduct(String name,int quantity, Long category_id){
        LOG.info("createProduct");

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);

        Category category = entityManager.find(Category.class,category_id);
        category.getProducts().add(product);
        product.setCategory(category);

        entityManager.persist(product);
    }

    public ProductDto findById(Long productId) {
        Product product = entityManager.find(Product.class,productId);
        Category category = product.getCategory();

        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getQuantity(), category);

        return productDto;
    }

    public void updateProduct(Long productId, String name, int quantity, Long categoryId) {
        LOG.info("updateProduct");

        Product product = entityManager.find(Product.class,productId);
        product.setName(name);
        product.setQuantity(quantity);

        Category oldCategory = product.getCategory();
        oldCategory.getProducts().remove(product);

        Category category = entityManager.find(Category.class,categoryId);
        category.getProducts().add(product);
        product.setCategory(category);
    }

    public void deleteProductsByIds(Collection<Long> productIds){
        LOG.info("deleteProductsByIds");

        for(Long productId : productIds){
            Product product = entityManager.find(Product.class,productId);
            entityManager.remove(product);
        }
    }

    public void addPhotoToProduct(Long productId, String fileName, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToProduct");
        ProductPhoto photo = new ProductPhoto();
        photo.setFileName(fileName);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);
        Product product = entityManager.find(Product.class, productId);
        if (product.getPhoto() != null) {
            entityManager.remove(product.getPhoto());
        }
        product.setPhoto(photo);
        photo.setProduct(product);
        entityManager.persist(photo);
    }

    public ProductPhotoDto findPhotoByCarId(Integer productId) {
        List<ProductPhoto> photos = entityManager
                .createQuery("SELECT p FROM ProductPhoto p WHERE p.product.id = :id", ProductPhoto.class)
                .setParameter("id", productId)
                .getResultList();
        if (photos.isEmpty()) {
            return null;
        }
        ProductPhoto photo = photos.get(0); // the first element
        return new ProductPhotoDto(photo.getId(), photo.getFileName(), photo.getFileType(),
                photo.getFileContent());
    }
}
