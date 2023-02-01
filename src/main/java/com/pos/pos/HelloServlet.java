package com.pos.pos;

import java.io.*;

import jakarta.annotation.security.DeclareRoles;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@DeclareRoles({"ADMIN", "DIRECTOR","CASHIER","VALID_CASHIER"})
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}