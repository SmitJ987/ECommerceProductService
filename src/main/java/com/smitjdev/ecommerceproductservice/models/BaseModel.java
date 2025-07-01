package com.smitjdev.ecommerceproductservice.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel
{
    @Id //jakarta persistance vala Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean isDeleted;
    private Date isCreatedAt;
    private Date isUpdatedAt;
}
