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
        //Pune parametrul primit prin GET(din URL) in photoId
        Long productId = Long.parseLong(request.getParameter("id"));

        //Cauta poza dupa id ul primit
        ProductPhotoDto photo = productsBean.findPhotoByProductId(productId);

        if(photo != null) {
            //Seteaza proprietatile pozei
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
