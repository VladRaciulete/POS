package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.PasswordBean;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.UsersBean;
import com.pos.pos.entities.User;
import com.pos.pos.entities.UserGroup;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@DeclareRoles({"ADMIN"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {
    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Trimite parametrul userGroups catre jsp
        request.setAttribute("userGroups", new String[] {"ADMIN", "DIRECTOR", "CASHIER"});

        //Face forward catre addUser.jsp
        request.getRequestDispatcher("/WEB-INF/pages/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ia parametrii primiti din form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroups = request.getParameterValues("user_groups");

        if (userGroups == null) {
            //Daca nu a fost selectat nici un usergroup, initializez usergroups
            userGroups = new String[0];
        }
        //Creeaza un user nou
        usersBean.createUser(username, email, password, Arrays.asList(userGroups));

        //Face forward catre servletul Users
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}
