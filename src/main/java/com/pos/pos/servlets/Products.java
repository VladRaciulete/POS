package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.common.UserDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.ejb.TransactionBean;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Products", value = "/Products")
public class Products extends HttpServlet {

    @Inject
    ProductsBean productsBean;
    @Inject
    CategoriesBean categoryBean;

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
        String card = "card";
        String cash = "cash";
        request.setAttribute("card", card);
        request.setAttribute("cash", cash);
        String[] deleteProductuctIdsAsString = request.getParameterValues("delete_product_ids");
        String[] buyProductIdsAsString = request.getParameterValues("buy_product_ids");
        String payment_type = String.valueOf(request.getParameterValues("payment"));

        // delete if Admin
        if(deleteProductuctIdsAsString != null) {
            List<Long> productIds = new ArrayList<>();
            for (String productIdAsString : deleteProductuctIdsAsString) {
                //Ia fiecare string din vector si il adauga in lista de id-uri
                productIds.add(Long.parseLong(productIdAsString));
            }
            //Sterge produsele
            productsBean.deleteProductsByIds(productIds);
            response.sendRedirect(request.getContextPath() + "/Products");
        }

        // buy if !Admin
        if(buyProductIdsAsString != null) {
            List<Long> productIds = new ArrayList<>();

            for (String buyProductIdAsString : buyProductIdsAsString) {
                productIds.add(Long.parseLong(buyProductIdAsString));
            }
            List<ProductDto> productsToSell = transactionBean.populate(productIds);

            request.setAttribute("productsToSell", productsToSell);


            //Cumpara produsele

            transactionBean.copyProductsToTransaction(productsToSell,"Sell", payment_type);
            productsBean.decreaseQuantity(productIds);
        }

        //Face forward catre servletul Products
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request,response);
    }
}
