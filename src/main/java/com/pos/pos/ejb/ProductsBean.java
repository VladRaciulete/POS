package com.pos.pos.ejb;

import com.pos.pos.common.*;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import com.pos.pos.entities.ProductPhoto;
import com.pos.pos.entities.TransactionDetails;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

@Stateless
public class ProductsBean {
    private static final Logger LOG = Logger.getLogger(ProductsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public Long findProductIdByProductName(String name) {
        //Gaseste id-ul primului produs care are numele introdus
        List <Long> productIds = entityManager.createQuery("SELECT p.id FROM Product p WHERE p.name LIKE :inputName",Long.class)
                .setParameter("inputName",name)
                .getResultList();

        //Returneaza id-ul produsului
        Long productId = productIds.get(0);
        return productId;
    }

    private List<ProductDto> copyProductsToDto(List<Product> products){
        //Primeste o lista de produse

        List<ProductDto> productsDto = new ArrayList<>();
        ProductDto var;
        Category category;
        for(Product elem : products){
            //Pentru fiecare produs

            category = elem.getCategory();
            //Creeaza un data transfer object cu proprietatile produsului
            var = new ProductDto(elem.getId(), elem.getName(), elem.getQuantity(), elem.getPrice(), category);

            //Adauga obiectul in lista de data transfer objects
            productsDto.add(var);
        }
        //Returneaza lista de data transfer objects
        return productsDto;
    }

    public ProductsByCategoryDto findAllProductsByCategoryId(Long categoryId){
        LOG.info("findAllProductsByCategoryId");
        //Gaseste toate produsele care apartin categoriei introduse

        try{
            //Cauta categoria care are id ul primit ca si parametru
            Category category = entityManager.find(Category.class,categoryId);

            //Pune in lista produsele care au categoria respectiva
            List<Product> products = entityManager.createQuery("SELECT p FROM Product p WHERE p.category =:category", Product.class)
                    .setParameter("category",category)
                    .getResultList();

            //Returneaza produsele convertite in data transfer objects
            return copyProductsToProductsByCategoryDto(products);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private ProductsByCategoryDto copyProductsToProductsByCategoryDto(List<Product> products){
        //Primeste lista de produse si le converteste in data transfer objects

        ProductDto productDto;
        List<ProductDto> ProductDtoList = new ArrayList<>();
        Category category = null;
        for(Product elem : products){
            //Pentru fiecare produs

            //Creeaza un data transfer object cu proprietatile produsului
            productDto = new ProductDto(elem.getId(), elem.getName(), elem.getQuantity(), elem.getPrice(), elem.getCategory());
            category = elem.getCategory();

            //Adauga obiectul in lista de data transfer objects
            ProductDtoList.add(productDto);
        }
        //Daca lista de data transfer objects nu e goala
        if(!ProductDtoList.isEmpty()){
            //Creeaza si returneaza obiectul de tip ProductsByCategoryDto
            ProductsByCategoryDto productsByCategoryDto = new ProductsByCategoryDto(category.getId(),category.getName(),ProductDtoList);
            return productsByCategoryDto;
            }
        return null;
    }

    public void createProduct(String name,int quantity, double price, Long category_id){
        LOG.info("createProduct");
        //Creeaza un produs nou
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        //Adauga noul produs la categoria corespunzatoare
        Category category = entityManager.find(Category.class,category_id);
        category.getProducts().add(product);
        product.setCategory(category);

        //Da persist la noul produs in baza de date
        entityManager.persist(product);
    }

    public ProductDto findById(Long productId) {
        Product product = entityManager.find(Product.class,productId);
        Category category = product.getCategory();

        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getQuantity(), product.getPrice(), category);

        return productDto;
    }

    public void updateProduct(Long productId, String name,int quantity, double price, Long categoryId){
        LOG.info("updateProduct");
        //Da update produsului

        //Cauta produsul
        Product product = entityManager.find(Product.class,productId);

        //Schimba proprietatile produsului cu cele introduse
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        //Sterge produsul din vechea categorie
        Category oldCategory = product.getCategory();
        oldCategory.getProducts().remove(product);

        //Adauga produsul in noua categorie
        Category category = entityManager.find(Category.class,categoryId);
        category.getProducts().add(product);
        product.setCategory(category);
    }

    public void deleteProductsByIds(Collection<Long> productIds){
        LOG.info("deleteProductsByIds");
        //Sterge produsele in functie de id
        for(Long productId : productIds){
            //Pentru fiecare id cauta produsul corespunzator si il sterge
            Product product = entityManager.find(Product.class,productId);
            entityManager.remove(product);
        }
    }

    public void buyProductsByIds(List<Long> productIds) {

    }

    public void addPhotoToProduct(Long productId, String fileName, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToProduct");
        //Adauga poza introdusa produsului corespunzator
        ProductPhoto photo = new ProductPhoto();
        photo.setFileName(fileName);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);
        //Cauta produsul
        Product product = entityManager.find(Product.class, productId);
        if (product.getPhoto() != null) {
            //Daca produsul are deja o poza ii da remove
            entityManager.remove(product.getPhoto());
        }
        //Adauga poza produsului
        product.setPhoto(photo);
        photo.setProduct(product);

        //Da persist la noua poza in baza de date
        entityManager.persist(photo);
    }

    public ProductPhotoDto findPhotoByProductId(Long productId) {
        //Cauta poza produsului
        List<ProductPhoto> photos = entityManager
                .createQuery("SELECT p FROM ProductPhoto p WHERE p.product.id = :id", ProductPhoto.class)
                .setParameter("id", productId)
                .getResultList();

        if (photos.isEmpty()) {
            return null;
        }
        ProductPhoto photo = photos.get(0);
        //Prima poza gasita
        //Querry ul returneaza o lista de elemente si de asta trebuie sa luam primul element gasit
        return new ProductPhotoDto(photo.getId(), photo.getFileName(), photo.getFileType(),
                photo.getFileContent());
    }


    public void decreaseQuantity(List<Long> productIds) {
        LOG.info("decrease quantity");
        // momentan scade cu 1 cantitatea din stoc

        for(Long productId : productIds){
            //Pentru fiecare id cauta produsul corespunzator si il sterge , produsul cu id ul pe care il cumparam
            Product product = entityManager.find(Product.class,productId);
            int quantity = product.getQuantity();
            product.setQuantity(quantity -1 );


        }
    }

    public TreeSet<ProductStatisticsDto> getProductStatistics() {
        LOG.info("getProductStatistics");
        TreeSet<ProductStatisticsDto> statistics = new TreeSet();

        List<Product> products = entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();


        List<TransactionDetails> transactionDet = entityManager.createQuery("SELECT t FROM TransactionDetails t", TransactionDetails.class)
                .getResultList();

        ProductStatisticsDto stats;
        int quantity;

        for(Product product : products) {
            quantity = 0;
            for (TransactionDetails transaction : transactionDet) {
                if(product.getId().equals(transaction.getProduct_id())){
                    quantity += 1;
                }
            }
            stats = new ProductStatisticsDto(product.getId(),product.getName(),quantity);
            LOG.info(stats.getProduct_name());
            statistics.add(stats);
        }

        return statistics;
    }
}
