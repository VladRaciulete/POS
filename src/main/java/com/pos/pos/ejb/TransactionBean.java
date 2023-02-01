package com.pos.pos.ejb;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.common.TransactionDto;
import com.pos.pos.entities.*;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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

        List<TransactionDto> transactionDto = new ArrayList<>();
        TransactionDto var;
        for( Transaction elem : transactions){

            var = new TransactionDto(elem.getTransaction_id(), elem.getTransaction_type(), elem.getPayment_type(), elem.getTotal(),1);

            transactionDto.add(var);
        }
        return transactionDto;
    }
    public TransactionDto findById(int transactionId) {
        Transaction transaction = entityManager.find(Transaction.class,transactionId);

        TransactionDto transactionDto = new TransactionDto(transaction.getTransaction_id(), transaction.getTransaction_type(), transaction.getPayment_type(),transaction.getTotal(), transaction.isScanned());

        return transactionDto;
    }

    /*
    Copiaza fiecare produs intr-o tranzactie sumara
     */
    public void copyProductsDtoToTransaction(List<ProductDto> productsToSellI, String transaction_type, String payment_type) {

       Transaction transaction = new Transaction();
       transaction.setTransaction_type(transaction_type);
       transaction.setPayment_type(payment_type);
      // transaction.setScanned(false);
        double total=0;
        for (ProductDto elem: productsToSellI) {
                total =total +elem.getPrice();
        }
        transaction.setTotal(total);
        entityManager.persist(transaction);
        LOG.info("Tabela 1 e plina ");

        for (ProductDto elem :productsToSellI ) {
            transactionDetails.copyProductToDetailedTransaction(elem, transaction.getTransaction_id());

        }

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


    public List<TransactionDto> findAllUnscannedTransactions(){
        LOG.info("findAllUnscannedTransactions");
        try{
            //Querry care selecteaza toate categoriile din tebelul Category
            TypedQuery<Transaction> typedQuery = entityManager.createQuery("SELECT c FROM Transaction c where c.scanned=1", Transaction.class);
            //Pune categoriile intr o lista
            List<Transaction> transactions = typedQuery.getResultList();
            //Apeleaza functia care returneaza categoriile ca DTO (data transfer objects)
            return copyTransactionsToDto(transactions);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

}
