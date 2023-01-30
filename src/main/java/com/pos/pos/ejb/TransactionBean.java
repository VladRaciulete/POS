package com.pos.pos.ejb;

import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.common.TransactionDto;
import com.pos.pos.entities.*;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class TransactionBean {
    private static final Logger LOG = Logger.getLogger(TransactionBean.class.getName());
@Inject
ProductsBean productsBean;
@Inject
TransactionDetailsBean transactionDetails;
    @PersistenceContext
    EntityManager entityManager;

    public Long findProductIdByProductName(String name) {
        //Gaseste id-ul primului produs care are numele introdus
        List<Long> productIds = entityManager.createQuery("SELECT p.id FROM Product p WHERE p.name LIKE :inputName",Long.class)
                .setParameter("inputName",name)
                .getResultList();

        //Returneaza id-ul produsului
        Long productId = productIds.get(0);
        return productId;
    }

    private List<TransactionDto> copyTransactionsToDto(List<Transaction> transactions){
        //Primeste o lista de produse

        List<TransactionDto> transactionDto = new ArrayList<>();
        TransactionDto var;
        for( Transaction elem : transactions){
            //Pentru fiecare produs


            //Creeaza un data transfer object cu proprietatile produsului
            var = new TransactionDto(elem.getTransaction_id(), elem.getTransaction_type(), elem.getPayment_type(), elem.getTotal());

            //Adauga obiectul in lista de data transfer objects
            transactionDto.add(var);
        }
        //Returneaza lista de data transfer objects
        return transactionDto;
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


    /*
    Copiaza fiecare produs intr-o tranzactie sumara
     */
    public void copyProductsToTransaction(List<ProductDto> productsToSellI, String transaction_type, String payment_type) {

       Transaction transaction = new Transaction();
       transaction.setTransaction_type(transaction_type);
       transaction.setPayment_type(payment_type);
        double total=0;
        for (ProductDto elem: productsToSellI) {
                total =total +elem.getPrice();
        }
        transaction.setTotal(total);
        entityManager.persist(transaction);
        LOG.info("Tabela 1 e plina ");


        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransactionDetails_id(3);
        for (ProductDto elem: productsToSellI) {

            transactionDetails.setTransaction_id(transaction.getTransaction_id());
            transactionDetails.setProduct_id(elem.getId());
            transactionDetails.setPrice(elem.getPrice());
            transactionDetails.setQuantity(1);
            LOG.info("Tabela 2 e aproape plina ");
            entityManager.persist(transactionDetails);
        }
        //transactionDetails.copyProductsToDetailedTransaction(productsToSellI, transaction.getTransaction_id());

    }



    /*
    Populeaza o lista cu produse
     */
    public List<ProductDto> populate(List<Long> productIds) {
        Product product = new Product();
        List<ProductDto> productsToSell = new ArrayList<>();
        for (Long elem:productIds) {
            product= entityManager.find(Product.class,elem);
            Category category = product.getCategory();

            ProductDto productDto = new ProductDto(product.getId(),product.getName(),product.getQuantity(),product.getPrice(),category);

            productsToSell.add(productDto);

        }
        return productsToSell;
    }
}
