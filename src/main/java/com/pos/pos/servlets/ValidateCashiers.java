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
        //Pune parametrul primit prin GET(din URL) in cashierId
        Long cashierId = Long.parseLong(request.getParameter("id"));

        //Adauga grupul de acces "VALID_CASHIER" in lista
        List <String> userGroups = new ArrayList<>();
        userGroups.add("VALID_CASHIER");

        //Cauta casierul corespunzator id-ului
        UserDto cashier = usersBean.findById(cashierId);

        //Apeleaza metoda updateUser cu parametrii userului(casierului) precedent si usergroup-ul modificat
        usersBean.updateUser(cashierId, cashier.getUsername(), cashier.getEmail(), cashier.getPassword(), userGroups);

        //Face forward catre servletul Cashiers
        response.sendRedirect(request.getContextPath() + "/Cashiers");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
