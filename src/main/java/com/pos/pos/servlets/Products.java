package com.pos.pos.servlets;

import com.pos.pos.common.*;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.TransactionBean;
import com.pos.pos.ejb.TransactionDetailsBean;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Products", value = "/Products")
public class Products extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Products.class.getName());

    @Inject
    ProductsBean productsBean;
    @Inject
    CategoriesBean categoryBean;
    @Inject
    TransactionDetailsBean transactionDetailsBean;
    @Inject
    TransactionBean transactionBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductsByCategoryDto> productsByCategoryList = new ArrayList<>();

        //Cauta toate categoriile
        List<CategoryDto> categories = categoryBean.findAllCategories();

        for(CategoryDto category : categories){
            //Pentru fiecare categorie cauta produsele categoriei respective
            ProductsByCategoryDto productsByCategoryDto = productsBean.findAllProductsByCategoryId(category.getId());

            //Adauga obiectul productsByCategoryDto in lista de productsByCategoryDto
            productsByCategoryList.add(productsByCategoryDto);
        }

        //Trimite catre jsp produsele dupa categorie
        request.setAttribute("productsByCategoryList", productsByCategoryList);

        //Trimite catre jsp pagina activa (Products)
        request.setAttribute("activePage","Products");

        //Face forward catre products.jsp
        request.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Pune id-urlie produselor selectate intr un vector de string
        //String card = "card";
        // String cash = "cash";
        // request.setAttribute("card", card);
        //request.setAttribute("cash", cash);
        String[] deleteProductuctIdsAsString = request.getParameterValues("delete_product_ids");


        // delete if Admin
        if (deleteProductuctIdsAsString != null) {
            List<Long> productIds = new ArrayList<>();
            for (String productIdAsString : deleteProductuctIdsAsString) {
                //Ia fiecare string din vector si il adauga in lista de id-uri
                productIds.add(Long.parseLong(productIdAsString));
            }
            //Sterge produsele
            productsBean.deleteProductsByIds(productIds);
            response.sendRedirect(request.getContextPath() + "/Products");
        }
        request.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(request,response);
    }
}
