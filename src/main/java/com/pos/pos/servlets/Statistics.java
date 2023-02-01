package com.pos.pos.servlets;

import com.pos.pos.common.ProductStatisticsDto;
import com.pos.pos.ejb.ProductsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

@WebServlet(name = "Statistics", value = "/Statistics")
public class Statistics extends HttpServlet {

    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage","Statistics");

        TreeSet<ProductStatisticsDto> stats = productsBean.getProductStatistics();
        request.setAttribute("statistics",stats);

        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
