package com.pos.pos.servlets;

import com.pos.pos.common.TransactionDetailsDto;
import com.pos.pos.common.TransactionDto;
import com.pos.pos.ejb.TransactionBean;
import com.pos.pos.ejb.TransactionDetailsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ApproveTransaction", value = "/ApproveTransaction")
public class ApproveTransaction extends HttpServlet {
    @Inject
    TransactionBean transactionBean;
    @Inject
    TransactionDetailsBean transactionDetailsBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TransactionDto> unscannedTransactions = transactionBean.findAllUnscannedTransactions();
        List<Integer> unscannedTransactionsIDs = new ArrayList<>();

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request,response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
