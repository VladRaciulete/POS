package com.pos.pos.servlets;

import com.pos.pos.common.ProductDto;
import com.pos.pos.ejb.ProductsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Products", value = "/Products")
public class Products extends HttpServlet {

    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductDto> products = productsBean.findAllProducts();
        request.setAttribute("products", products);

        request.setAttribute("activePage","Products");
        request.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
