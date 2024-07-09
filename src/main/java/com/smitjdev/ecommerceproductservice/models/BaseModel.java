package com.smitjdev.ecommerceproductservice.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseModel implements Serializable {

    private Long id;
    private boolean isDeleted;
    private Date isCreatedAt;
    private Date isUpdatedAt;
}
