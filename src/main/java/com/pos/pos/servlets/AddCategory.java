package com.pos.pos.servlets;

import com.pos.pos.ejb.CategoriesBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@DeclareRoles({"ADMIN","DIRECTOR"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN","DIRECTOR"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN","DIRECTOR"})})
@WebServlet(name = "AddCategory", value = "/AddCategory")
public class AddCategory extends HttpServlet {

    @Inject
    CategoriesBean categoriesBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Face forward catre addCategory.jsp
        request.getRequestDispatcher("/WEB-INF/pages/addCategory.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ia parametrul primit din form si creeaza noua categorie
        String name = request.getParameter("name");
        categoriesBean.createCategory(name);

        //Face forward catre servletul Products
        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
