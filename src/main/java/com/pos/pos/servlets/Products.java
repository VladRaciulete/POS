package com.pos.pos.servlets;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.common.ProductsByCategoryDto;
import com.pos.pos.ejb.CategoriesBean;
import com.pos.pos.ejb.ProductsBean;
import com.pos.pos.entities.Category;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<ProductDto> products = productsBean.findAllProducts();
        //request.setAttribute("products", products);

        List<ProductsByCategoryDto> productsByCategoryList = new ArrayList<>();
        List<CategoryDto> categories = categoryBean.findAllCategories();

        for(CategoryDto category : categories){
            ProductsByCategoryDto productsByCategoryDto = productsBean.findAllProductsByCategoryId(category.getId());
            productsByCategoryList.add(productsByCategoryDto);
        }

        request.setAttribute("productsByCategoryList", productsByCategoryList);


        request.setAttribute("activePage","Products");
        request.getRequestDispatcher("/WEB-INF/pages/products.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIdsAsString = request.getParameterValues("product_ids");
        if(productIdsAsString != null){
            List<Long> productIds = new ArrayList<>();
            for(String productIdAsString : productIdsAsString){
                productIds.add(Long.parseLong(productIdAsString));
            }
            productsBean.deleteProductsByIds(productIds);
        }
        response.sendRedirect(request.getContextPath() + "/Products");
    }
}
