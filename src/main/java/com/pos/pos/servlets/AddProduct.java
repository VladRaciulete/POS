package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@DeclareRoles({"ADMIN","DIRECTOR"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN","DIRECTOR"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN","DIRECTOR"})})
@MultipartConfig
@WebServlet(name = "AddProduct", value = "/AddProduct")
public class AddProduct extends HttpServlet {

    @Inject
    ProductsBean productsBean;

    @Inject
    CategoriesBean categoriesBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Pune categoriile intr o lista
        List<CategoryDto> categories = categoriesBean.findAllCategories();

        //Trimite parametrul catre jsp
        request.setAttribute("categories",categories);

        //Face forward catre addProduct.jsp
        request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ia parametrii primiti din form
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        Long category_id = Long.parseLong(request.getParameter("category_id"));

        //Creeaza un produs nou
        productsBean.createProduct(name, quantity, price, category_id);

        //Cauta id-ul produsului
        Long productId = productsBean.findProductIdByProductName(name);

        //Ia file ul primit din form (poza)
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();
        long fileSize = filePart.getSize();
        byte[] fileContent = new byte[(int) fileSize];
        filePart.getInputStream().read(fileContent);

        //Adauga poza produsului
        productsBean.addPhotoToProduct(productId,fileName,fileType,fileContent);

        //Face forward catre servletul Products
        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
