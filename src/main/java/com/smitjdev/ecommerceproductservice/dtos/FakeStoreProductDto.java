package com.smitjdev.ecommerceproductservice.dtos;

import com.smitjdev.ecommerceproductservice.models.Category;
import com.smitjdev.ecommerceproductservice.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakeStoreProductDto {

    //this is kind of a mapper class

    //the attribute names should be exactly same as the ones written in json format in fakestore

    //only then will we get the response...

    private String title;
    private String description;
    private Long id;
    private double price;
    private String category;
    private String image;

    public Product toProduct()
    {
        Category cat = new Category();
        cat.setTitle(category);

        Product product = new Product(title,description,price,image,cat);
        product.setId(id);

        //or do like:
        //product.setTitle(title), setImage... and all

        return product;
    }

}
