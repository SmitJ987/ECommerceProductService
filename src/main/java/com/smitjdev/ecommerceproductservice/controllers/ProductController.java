package com.smitjdev.ecommerceproductservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.smitjdev.ecommerceproductservice.models.Category;
import com.smitjdev.ecommerceproductservice.models.Product;
import com.smitjdev.ecommerceproductservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//"@RestController" this will tell spring: that this class is very important
//when we hit some endpoints, this class is having info about which endpoint will run which method
//and this class will tell the DispatchServlet class of spring: to run a particular method
//based on any end point is hit by client.

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    //seven end points:
    //1: getAllProducts
    @GetMapping(value = "/products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    //2: getSingleProduct
    //name of endPoint should be around resource name only: nothing else
    @GetMapping("/products/{id}")
    public Product getSingleProduct(@PathVariable("id") Long productId)
    {
        Product p = productService.getSingleProduct(productId);
        return p;
    }

    //3: delete a product
    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId)
    {
        Product deletedProduct = productService.deleteProduct(productId);
        return deletedProduct;
    }

    //4: add/create new product
    //create means : Post: so use PostMapping
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product)
    {
        System.out.println("incoming product:"+product.toString());
        Product createdProduct = productService.createProduct(product.getTitle(),product.getPrice(),product.getDescription(),product.getImageUrl(), product.getCategory());
        return createdProduct;
    }


    //5: getAllCategories
    @GetMapping("/categories")
    public List<Category> getAllCategories()
    {
        List<Category> allCategories = productService.getAllCategories();
        return allCategories;
    }

    //6: getAllProductsFromASpecificCategory
    @GetMapping("/products/byCategory/{category}")
    public List<Product> getProductsInCategory(@PathVariable("category") String category)
    {
        List<Product> productList = productService.getProductsInCategory(category);
        return productList;
    }

    //7: update product
    //PUT: required to give all the attributes: whether  you want to change or not
    //if not want to change: then pass old value
    //to change: pass new value

    //the attributes: which are not passed: those attribute's old value would be replaced by
    //null or default value based on data type
    //and you also need to give productId: i.e which product to be updated
    @PutMapping(value="products/{id}")
    public Product updateProductViaPut(@RequestBody Product product, @PathVariable("id") Long productId)
    {
        //System.out.print(product);

        //set the incoming productId to the incoming Product also
        product.setId(productId);

        Product updatedProduct = productService.updateProductViaPut(product);

        return updatedProduct;
    }

    //PUT: when to use: PUT is for Create_Or_Replace
    //1. when URL is confirmed: on target URL: if something is already there: it would be replaced
    //2. if nothing is there on target URL: then at that place: new resource would be created with requestBody values

    //POST: when to use
    //1. when the URL is not known to us: i.e we just tell: make new product: and apply whatever auto generated id you want on it
    //POST is for creating

    //Patch:   to use: when we want to change some specific attributes only: on a target URL

//    @PatchMapping(value="/products/{id}")
//    public Product updateProductViaPatch(@RequestBody Product product, @PathVariable("id") Long productId)
//    {
//        Product updatedProduct = productService.updateProductViaPatch(product);
//        return updatedProduct;
//    }

    @PatchMapping(value="/products/{id}",consumes = "application/json-patch+json")
    public Product partiallyUpdateProductViaPatch(@RequestBody JsonPatch patch, @PathVariable("id") Long id)
    {
        ResponseEntity<Product> response = productService.updateProductViaPatch(patch,id);
        return response.getBody();
    }




    //for understanding of how path variables work
//    @GetMapping("/printName/{name}/times/{count}")
//    public String printName(@PathVariable("name") String name, @PathVariable("count") int count)
//    {
//        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<count;i++)
//        {
//            sb.append(name+" ");
//        }
//
//        return sb.toString();
//    }

}
