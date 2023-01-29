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
import java.util.Collection;
import java.util.List;

@DeclareRoles({"ADMIN"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"ADMIN"})})
@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Cauta toti userii
        List<UserDto> users = usersBean.findAllUsers();

        //trimite userii catre jsp
        request.setAttribute("users",users);

        //Trimite catre jsp pagina activa (Users)
        request.setAttribute("activePage","Users");

        //Face forward catre users.jsp
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Pune id-urlie userilor selectati intr un vector de string
        String[] userIdsAsString = request.getParameterValues("user_ids");

        if(userIdsAsString != null){
            List<Long> userIds = new ArrayList<>();
            for(String userIdAsString : userIdsAsString){
                //Ia fiecare string din vector si il adauga in lista de id-uri
                userIds.add(Long.parseLong(userIdAsString));
            }
            //Sterge userii
            usersBean.deleteUsersByIds(userIds);
        }
        //Face forward catre servletul Users
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}
