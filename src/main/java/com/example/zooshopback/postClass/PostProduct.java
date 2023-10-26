package com.example.zooshopback.postClass;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostProduct {

    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Long categoryId;
}
