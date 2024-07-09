package com.smitjdev.ecommerceproductservice.models;

import com.smitjdev.ecommerceproductservice.dtos.FakeStoreProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel{

    private String title;
    private String description;
    private double price;
    private String imageUrl;
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
