package com.pos.pos.servlets;

import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.UsersBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DeclareRoles({"DIRECTOR"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"DIRECTOR"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"DIRECTOR"})})
@WebServlet(name = "ValidateCashiers", value = "/ValidateCashiers")
public class ValidateCashiers extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long cashierId = Long.parseLong(request.getParameter("id"));
        List <String> userGroups = new ArrayList<>();
        userGroups.add("VALID_CASHIER");
        UserDto cashier = usersBean.findById(cashierId);
        usersBean.updateUser(cashierId, cashier.getUsername(), cashier.getEmail(), cashier.getPassword(), userGroups);

        response.sendRedirect(request.getContextPath() + "/Cashiers");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
