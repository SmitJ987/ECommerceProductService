package com.smitjdev.ecommerceproductservice.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.smitjdev.ecommerceproductservice.models.Category;
import com.smitjdev.ecommerceproductservice.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    public List<Product> getAllProducts();

    public Product getSingleProduct(Long id);

    public Product deleteProduct(Long id);

    //this would be tightly coupled with Product: and would always ask for Product in argument
    //not a good practise: instead whatever params are required
    //directly ask for them as args
    //public Product createProduct(Product product);
    public Product createProduct(String title, double price, String description, String imageUrl, Category category);

    public List<Category> getAllCategories();

    public List<Product> getProductsInCategory(String categoryName);

//    public Product updateProduct(Product product, Long productId);
    public Product updateProductViaPut(Product product);

    public ResponseEntity<Product> updateProductViaPatch(JsonPatch patch, Long productId);
}
