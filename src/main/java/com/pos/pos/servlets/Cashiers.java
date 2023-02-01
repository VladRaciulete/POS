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
import java.util.List;

@DeclareRoles({"DIRECTOR"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"DIRECTOR"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"DIRECTOR"})})
@WebServlet(name = "Cashiers", value = "/Cashiers")
public class Cashiers extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Cauta toti casierii care nu au fost validati si ii trimite catre jsp
        List<UserDto> invalidCashiers = usersBean.findAllInvalidCashiers();
        request.setAttribute("invalidCashiers",invalidCashiers);

        //Cauta toti casierii validati si ii trimite catre jsp
        List<UserDto> validCashiers = usersBean.findAllValidCashiers();
        request.setAttribute("validCashiers",validCashiers);

        //Trimite catre jsp pagina activa (Cashiers)
        request.setAttribute("activePage","Cashiers");

        //Face forward catre cashiers.jsp
        request.getRequestDispatcher("/WEB-INF/pages/cashiers.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
