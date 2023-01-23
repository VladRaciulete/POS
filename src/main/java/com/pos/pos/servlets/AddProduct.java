package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AddProduct", value = "/AddProduct")
public class AddProduct extends HttpServlet {

    @Inject
    ProductsBean productsBean;

    @Inject
    CategoriesBean categoriesBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryDto> categories = categoriesBean.findAllCategories();
        request.setAttribute("categories",categories);
        request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Long category_id = Long.parseLong(request.getParameter("category_id"));

        productsBean.createProduct(name, quantity,category_id);




        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
