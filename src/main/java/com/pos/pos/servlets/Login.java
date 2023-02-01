package com.pos.pos.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Face forward catre login.jsp
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Trimite parametrul message catre jsp (va fi folosit in caz de eroare)
        request.setAttribute("message", "Username or password incorrect");

        //Face forward catre login.jsp
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
