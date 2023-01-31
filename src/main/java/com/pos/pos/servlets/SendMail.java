package com.pos.pos.servlets;

import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.MailBean;
import com.pos.pos.ejb.UsersBean;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "SendMail", value = "/SendMail")
public class SendMail extends HttpServlet {
    @Inject
    MailBean mailBean;

    @Inject
    UsersBean usersBean;
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
