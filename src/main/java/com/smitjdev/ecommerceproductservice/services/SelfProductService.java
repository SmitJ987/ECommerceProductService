package com.smitjdev.ecommerceproductservice.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.smitjdev.ecommerceproductservice.models.Category;
import com.smitjdev.ecommerceproductservice.models.Product;
import com.smitjdev.ecommerceproductservice.repositories.CategoryRepository;
import com.smitjdev.ecommerceproductservice.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService
{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long id) {
        return productRepository.findByIdIs(id);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product deletedProduct = productRepository.findByIdIs(id);

        if(deletedProduct != null)
        {
            productRepository.deleteById(id);
            return deletedProduct;
        }
        else {
            //from here throw exception that no product found
            //for now returning null though
            return null;
        }
    }

    @Override
    public Product createProduct(String title, double price, String description, String imageUrl, Category incomingCategory) {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);


        //doing checks by ourselves: if we not use cascade type as persist in product class for category variable
        //this if check is with different purpose:
        //like the work of persist
        //first check if this category exists or not in db
//        Category cat =  categoryRepository.findByTitle(incomingCategory.getTitle());
//
//        if(cat==null)
//        {
//            cat = new Category();
//            cat.setTitle(incomingCategory.getTitle());
//
//              //save it in db
//            Category savedCat = categoryRepository.save(cat);
//            product.setCategory(savedCat);
//        }
//        else
//        {
//            //ye to exist karti hai is name se cat in db
//            //so yehi set karo: not incoming: usme id nai hogi: jo title se find ki hai wo vali
//            product.setCategory(cat);
//        }

        //now we are using cascade = CascadeType.PERSIST inside product class
        //on Category variable
        //here if check is with different purpose
        //check in db is cat with this title already exists: so that we
        //do not create duplicate categories
        Category cat = categoryRepository.findByTitle(incomingCategory.getTitle());
        if(cat !=null)
        {
            product.setCategory(cat);
        }
        else
        {
            product.setCategory(incomingCategory);
        }



        //now save the product
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsInCategory(String categoryName) {
        return productRepository.getAllByCategoryTitle(categoryName);
    }

    @Override
    public Product updateProductViaPut(Product incomingProduct) {
//        Product savedProduct = productRepository.save(incomingProduct);
//        return savedProduct;
        //this saved product above is getting category title and whole category variable as null
        //find why so...
        return null;
    }

    @Override
    public ResponseEntity<Product> updateProductViaPatch(JsonPatch patch, Long productId) {
        return null;
    }

    @Override
    public Category deleteCategory(Long id)
    {
        Category cat = categoryRepository.findCategoryById(id);

        if(cat != null)
        {
            categoryRepository.deleteById(id);
            return cat;
        }
        else
        {
            //throw exception from here: that category with given id not found
            return null;
        }
    }

}
