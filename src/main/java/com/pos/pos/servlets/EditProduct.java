package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.UsersBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.print.attribute.IntegerSyntax;
import java.io.IOException;
import java.util.List;

@DeclareRoles({"ADMIN"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "EditProduct", value = "/EditProduct")
public class EditProduct extends HttpServlet {

    @Inject
    CategoriesBean categoriesBean;

    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryDto> categories = categoriesBean.findAllCategories();
        request.setAttribute("categories",categories);

        Long productId = Long.parseLong(request.getParameter("id"));
        ProductDto product = productsBean.findById(productId);
        request.setAttribute("product",product);

        request.getRequestDispatcher("/WEB-INF/pages/editProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.parseLong(request.getParameter("product_id"));
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Long categoryId = Long.parseLong(request.getParameter("category_id"));

        productsBean.updateProduct(productId, name, quantity, categoryId);
        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
