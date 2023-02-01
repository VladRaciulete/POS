package com.pos.pos.ejb;

import com.pos.pos.common.ProductDto;
import com.pos.pos.common.TransactionDetailsDto;
import com.pos.pos.common.TransactionDto;
import com.pos.pos.entities.Transaction;
import com.pos.pos.entities.TransactionDetails;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class TransactionDetailsBean {
    private static final Logger LOG = Logger.getLogger(TransactionDetailsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;
    /*
       Copiaza intr-o tabela fiecare produs cu id-ul tranzactiei din care face parte
   */
    public void copyProductToDetailedTransaction(ProductDto elem, int transaction_id) {
            TransactionDetails transactionDetails = new TransactionDetails();

                transactionDetails.setTransaction_id(transaction_id);
                transactionDetails.setProduct_id(elem.getId());
                transactionDetails.setPrice(elem.getPrice());
                transactionDetails.setQuantity(1);
                LOG.info("Tabela 2 e aproape plina ");
                entityManager.persist(transactionDetails);

        }

    public List<TransactionDetailsDto> copyTransactionDetailsToDto(List<TransactionDetails> transactionDetails){
        List<TransactionDetailsDto> transactionDetailsDto = new ArrayList<>();
        TransactionDetailsDto var;
        for( TransactionDetails elem : transactionDetails){

            var = new TransactionDetailsDto(
            elem.getTransaction_id(),elem.getPrice(),elem.getProduct_id(), elem.getQuantity(),elem.getTransactionDetails_id());
            transactionDetailsDto.add(var);
        }
        //Returneaza lista de data transfer objects
        return transactionDetailsDto;
    }

    public List<TransactionDetailsDto> findAllUnscannedTransactionDetails(List<Integer> unscannedTransactionsIDs){
        LOG.info("findAllUnscannedTransactionDetails");
        try{
            List<TransactionDetails> transactionDetails = new ArrayList<>();
            for (Integer elem:unscannedTransactionsIDs) {
                TypedQuery<TransactionDetails> typedQuery = entityManager.createQuery(
        "SELECT c FROM TransactionDetails c WHERE c.transaction_id =: elem", TransactionDetails.class).setParameter("elem",elem);
                 transactionDetails.add(typedQuery.getSingleResult());
            }

            return copyTransactionDetailsToDto(transactionDetails);

        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    }

