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

@DeclareRoles({"ADMIN","DIRECTOR"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN","DIRECTOR"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN","DIRECTOR"})})
@WebServlet(name = "EditProduct", value = "/EditProduct")
public class EditProduct extends HttpServlet {

    @Inject
    CategoriesBean categoriesBean;

    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Cauta toate categoriile si le trimite catre jsp
        List<CategoryDto> categories = categoriesBean.findAllCategories();
        request.setAttribute("categories",categories);

        //Pune parametrul primit prin GET(din URL) in productId
        Long productId = Long.parseLong(request.getParameter("id"));

        //Cauta produsul corespunzator id-ului si il trimite catre jsp
        ProductDto product = productsBean.findById(productId);
        request.setAttribute("product",product);

        //Face forward catre editProduct.jsp
        request.getRequestDispatcher("/WEB-INF/pages/editProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ia parametrii primiti din form
        Long productId = Long.parseLong(request.getParameter("product_id"));
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        Long categoryId = Long.parseLong(request.getParameter("category_id"));

        //Apeleaza metoda update product cu parametrii primiti
        productsBean.updateProduct(productId, name, quantity, price, categoryId);

        //Face forward catre servletul Products
        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
