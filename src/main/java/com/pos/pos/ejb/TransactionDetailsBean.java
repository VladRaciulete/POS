package com.pos.pos.ejb;

import com.pos.pos.common.ProductDto;
import com.pos.pos.entities.TransactionDetails;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
    public void copyProductsToDetailedTransaction(ProductDto elem, int transaction_id) {
            TransactionDetails transactionDetails = new TransactionDetails();

                transactionDetails.setTransaction_id(transaction_id);
                transactionDetails.setProduct_id(elem.getId());
                transactionDetails.setPrice(elem.getPrice());
                transactionDetails.setQuantity(1);
                LOG.info("Tabela 2 e aproape plina ");
                entityManager.persist(transactionDetails);

        }
    }

