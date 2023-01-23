package com.pos.pos.ejb;

import com.pos.pos.common.ProductDto;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
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
}
