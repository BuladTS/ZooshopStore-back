package com.example.zooshopback.controller;

import com.example.zooshopback.model.Product;
import com.example.zooshopback.model.ProductImage;
import com.example.zooshopback.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/product-image")
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public List<ProductImage> findAll() {
        return productImageService.findAll();
    }
}
