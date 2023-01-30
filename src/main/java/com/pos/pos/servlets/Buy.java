package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.TransactionBean;
import com.pos.pos.entities.Product;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Buy", value = "/Buy")
public class Buy extends HttpServlet {
    @Inject
    TransactionBean transactionBean;
    @Inject
    ProductsBean productsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Trimite catre jsp pagina activa (Products)
        request.setAttribute("activePage","Buy");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] buyProductIdsAsString = request.getParameterValues("buy_product_ids");

        if(buyProductIdsAsString != null) {
            List<Long> productIds = new ArrayList<>();

            for (String buyProductIdAsString : buyProductIdsAsString) {
                productIds.add(Long.parseLong(buyProductIdAsString));
            }
            List<ProductDto> productsToSell = transactionBean.populate(productIds);

            request.setAttribute("productsToSell", productsToSell);
            //Cumpara produsele
            transactionBean.copyProductsToTransaction(productsToSell,"Sell","card");
            productsBean.decreaseQuantity(productIds);
        }

        //Face forward catre checkout.jsp
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request,response);
    }
}
