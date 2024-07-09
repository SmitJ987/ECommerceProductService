package com.smitjdev.ecommerceproductservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseModel implements Serializable {
    private String title;

    private List<Product> products;

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }
}
