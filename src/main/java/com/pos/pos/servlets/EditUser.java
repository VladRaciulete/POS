package com.pos.pos.servlets;

import com.pos.pos.common.ProductDto;
import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.UsersBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@DeclareRoles({"ADMIN"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("userGroups", new String[] {"ADMIN", "DIRECTOR", "CASHIER"});

        Long userId = Long.parseLong(request.getParameter("id"));
        UserDto user = usersBean.findById(userId);
        request.setAttribute("user",user);

        request.getRequestDispatcher("/WEB-INF/pages/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroups = request.getParameterValues("user_groups");
        if (userGroups == null) {
            userGroups = new String[0];
        }

        usersBean.updateUser(userId, username, email, password, Arrays.asList(userGroups));
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}
