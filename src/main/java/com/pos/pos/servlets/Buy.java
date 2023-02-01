package com.pos.pos.servlets;

import com.pos.pos.common.ProductDto;
import com.pos.pos.common.TransactionDetailsDto;
import com.pos.pos.common.TransactionDto;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.TransactionBean;
import com.pos.pos.ejb.TransactionDetailsBean;
import com.pos.pos.entities.TransactionDetails;
import com.sun.tools.sjavac.Log;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
@DeclareRoles({"ADMIN","VALID_CASHIER"})
@WebServlet(name = "Buy", value = "/Buy")
public class Buy extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Buy.class.getName());
    @Inject
    TransactionBean transactionBean;
    @Inject
    ProductsBean productsBean;
    @Inject
    TransactionDetailsBean transactionDetailsBean;
    @PersistenceContext
    EntityManager entityManager;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage","Buy");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] buyProductIdsAsString = request.getParameterValues("buy_product_ids");
        String payment_type = request.getParameter("payment");
        // buy if !Admin
        if(buyProductIdsAsString != null) {
            List<Long> productIds = new ArrayList<>();

            for (String buyProductIdAsString : buyProductIdsAsString) {
                productIds.add(Long.parseLong(buyProductIdAsString));
            }
            List<ProductDto> productsToSell = transactionBean.populate(productIds);

            request.setAttribute("productsToSell", productsToSell);


            //Cumpara produsele

            transactionBean.copyProductsDtoToTransaction(productsToSell,"Sell", payment_type);
            productsBean.decreaseQuantity(productIds);
        }



        List<TransactionDto> unscannedTransactions = transactionBean.findAllUnscannedTransactions();
        List<Integer> unscannedTransactionsIDs = new ArrayList<>();
       // int transaction_id = Integer.parseInt(request.getParameter("scan_transaction_id"));
       // transactionBean.scanTransaction(transaction_id);

        for (TransactionDto elem:unscannedTransactions) {
            unscannedTransactionsIDs.add(elem.getTransaction_id());
            LOG.info(""+elem.getTransaction_id());
        }
        List<TransactionDetailsDto> transactionDetailsDtoList = transactionDetailsBean.findAllUnscannedTransactionDetails(unscannedTransactionsIDs);

        request.setAttribute("unscannedTransactions",unscannedTransactions);
        request.setAttribute("transactionDetailsDtoList",transactionDetailsDtoList);

        //Face forward catre servletul Products
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request,response);
    }
}

