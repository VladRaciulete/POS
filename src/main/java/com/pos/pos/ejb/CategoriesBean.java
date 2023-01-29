package com.pos.pos.ejb;

import com.pos.pos.common.CategoryDto;
import com.pos.pos.common.ProductDto;
import com.pos.pos.entities.Category;
import com.pos.pos.entities.Product;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CategoriesBean {
    private static final Logger LOG = Logger.getLogger(ProductsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CategoryDto> findAllCategories(){
        LOG.info("findAllCategories");
        try{
            //Querry care selecteaza toate categoriile din tebelul Category
            TypedQuery<Category> typedQuery = entityManager.createQuery("SELECT c FROM Category c", Category.class);
            //Pune categoriile intr o lista
            List<Category> categories = typedQuery.getResultList();
            //Apeleaza functia care returneaza categoriile ca DTO (data transfer objects)
            return copyCategoriesToDto(categories);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private List<CategoryDto> copyCategoriesToDto(List<Category> categories){
        //Primeste lista de categorii
        List<CategoryDto> categoryDto = new ArrayList<>();
        CategoryDto var;
        for(Category elem : categories){
            //Pentru fiecare categorie din lista
            //Creeaza un obiect de transfer si il adauga in lista "categoryDto"
            var = new CategoryDto(elem.getId(), elem.getName());
            categoryDto.add(var);
        }
        return categoryDto;
    }

    public void createCategory(String name){
        LOG.info("createCategory");
        //Creaza o noua categorie

        Category category = new Category();
        category.setName(name);

        //Da persist la noua categorie in baza de date
        entityManager.persist(category);
    }
}
