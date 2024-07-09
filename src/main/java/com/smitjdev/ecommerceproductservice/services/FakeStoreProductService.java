package com.smitjdev.ecommerceproductservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.smitjdev.ecommerceproductservice.dtos.FakeStoreProductDto;
import com.smitjdev.ecommerceproductservice.models.Category;
import com.smitjdev.ecommerceproductservice.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Product> getAllProducts()
    {
        String url = "https://fakestoreapi.com/products";

        //what fakestore will return is it's own definition
        //which is different from our model

        //so first get fakestore response in terms of a DTO (Data Transfer Object)
        //so no values are missed...

        FakeStoreProductDto[] fsDtoArray = restTemplate.getForObject(url,FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto f : fsDtoArray)
        {
            Product p = f.toProduct();
            products.add(p);
        }

        return products;
    }

    @Override
    public Product getSingleProduct(Long id)
    {
        String url =  "https://fakestoreapi.com/products/"+id;

        //first get that product in terms of FakeStoreDto,
        FakeStoreProductDto fsDto = restTemplate.getForObject(url,FakeStoreProductDto.class);

        Product p = fsDto.toProduct();
        return p;
    }

    @Override
    public Product deleteProduct(Long id)
    {
        String url =  "https://fakestoreapi.com/products/"+id;
        //in terms of FSPDTO,
        FakeStoreProductDto deletedFakeStoreProductDto = restTemplate.getForObject(url,FakeStoreProductDto.class);

        return deletedFakeStoreProductDto.toProduct();
    }

    @Override
    public Product createProduct(String title, double price, String description, String imageUrl, Category cat)
    {
        FakeStoreProductDto fsProductDto = new FakeStoreProductDto();

        fsProductDto.setTitle(title);
        fsProductDto.setPrice(price);
        fsProductDto.setDescription(description);
        fsProductDto.setImage(imageUrl);
        fsProductDto.setCategory(cat.getTitle());

        String url = "https://fakestoreapi.com/products";

        FakeStoreProductDto responseBody = restTemplate.postForObject(url,fsProductDto,FakeStoreProductDto.class);

        return responseBody.toProduct();
    }

    @Override
    public List<Category> getAllCategories()
    {
        String url = "https://fakestoreapi.com/products/categories";

        //fakestore will respond all categories in terms of string only
        String[] allCats = restTemplate.getForObject(url,String[].class);

        List<Category> categories = new ArrayList<>();

        for(String st : allCats)
        {
            Category cat = new Category();
            cat.setTitle(st);

            categories.add(cat);
        }

        return categories;
    }

    @Override
    public List<Product> getProductsInCategory(String categoryName)
    {
        String url = "https://fakestoreapi.com/products/category/"+categoryName;

        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(url,FakeStoreProductDto[].class);
        List<Product> productList = new ArrayList<>();

        for(FakeStoreProductDto f : fakeStoreProductDtos)
        {
            Product p = f.toProduct();
            productList.add(p);
        }

        return productList;
    }

//    @Override
//    public Product updateProduct(Product product, Long productId)
//    {
//        String url = "https://fakestoreapi.com/products/" + productId;
//
//        FakeStoreProductDto fakeStoreProductDto = product.toFakeStoreProductDto();
//
////         restTemplate.put(url,fakeStoreProductDto,FakeStoreProductDto.class);
////
////        Product updatedProduct = fsProductDto.toProduct();
////        return updatedProduct;
//        return null;
//
//        //implement put and patch today only
//    }

    @Override
    public Product updateProductViaPut(Product product)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");

        FakeStoreProductDto fakeStoreProductDto = product.toFakeStoreProductDto();

        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto,headers);

        String url = "https://fakestoreapi.com/products/" + product.getId();

        ResponseEntity<FakeStoreProductDto> responseEntity =
            restTemplate.exchange(url,
                    HttpMethod.PUT,
                    requestEntity,
                    FakeStoreProductDto.class);

        Product updatedProduct = responseEntity.getBody().toProduct();
        return updatedProduct;
    }

    @Override
    public ResponseEntity<Product> updateProductViaPatch(JsonPatch patch, Long productId)
    {
        try
        {
            Product product  = getSingleProduct(productId);

            Product patchedProduct = applyPatchToProduct(patch,product);

            updateProductViaPut(patchedProduct);
            return ResponseEntity.ok(patchedProduct);

        }
        catch(JsonPatchException|JsonProcessingException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //helper method: use in: updateProductViaPatch method
    public Product applyPatchToProduct(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patchedNode = patch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patchedNode,Product.class);
    }
}
