package com.pos.pos.servlets;

import com.pos.pos.common.ProductPhotoDto;
import com.pos.pos.ejb.ProductsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProductPhotos", value = "/ProductPhotos")
public class ProductPhotos extends HttpServlet {

    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer photoId = Integer.parseInt(request.getParameter("id"));
        ProductPhotoDto photo = productsBean.findPhotoByCarId(photoId);
        if(photo != null) {
            response.setContentType(photo.getFileType());
            response.setContentLength(photo.getFileContent().length);
            response.getOutputStream().write(photo.getFileContent());
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
