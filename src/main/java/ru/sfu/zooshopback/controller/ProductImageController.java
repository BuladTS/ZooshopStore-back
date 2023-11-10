package ru.sfu.zooshopback.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.service.ProductImageService;
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

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource findById(@PathVariable String name) {
        return productImageService.findByName(name);
    }
}
