package com.smitjdev.ecommerceproductservice.models;

import com.smitjdev.ecommerceproductservice.dtos.FakeStoreProductDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Product extends BaseModel{

    private String title;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST) //means when put new product: if category doesn't exist: it will first create the category in category table, and then it will save the product.
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                '}';
    }

    public FakeStoreProductDto toFakeStoreProductDto()
    {
        FakeStoreProductDto fsProductDto = new FakeStoreProductDto();
        fsProductDto.setTitle(title);
        fsProductDto.setDescription(description);
        fsProductDto.setPrice(price);
        fsProductDto.setImage(imageUrl);
        fsProductDto.setId(getId());
        fsProductDto.setCategory(category.getTitle());

        return fsProductDto;
    }
}
