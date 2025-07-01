package com.smitjdev.ecommerceproductservice.repositories;

import com.smitjdev.ecommerceproductservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long>
{
    Category save(Category category);

    Category findCategoryById(Long id);
//    Category findCategoryByTitle();   //this is more time consuming
    //we just want to check if category with an Id exists or not
    //for that use exists
    boolean existsByTitle(String title);

    Category findByTitle(String title);

    @Override
    List<Category> findAll();

    void deleteById(Long id);
}
