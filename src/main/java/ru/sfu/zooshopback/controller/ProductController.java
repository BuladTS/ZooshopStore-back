package ru.sfu.zooshopback.controller;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.service.ProductImageService;
import ru.sfu.zooshopback.service.ProductImageStorageService;
import ru.sfu.zooshopback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshopback.wrapper.ImageItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductImageStorageService productImageStorageService;

    @Autowired
    public ProductController(
            ProductService productService,
            ProductImageService productImageService,
            ProductImageStorageService productImageStorageService
    ) {
        this.productService = productService;
        this.productImageService = productImageService;
        this.productImageStorageService = productImageStorageService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        log.info("Find all products");
        return new ResponseEntity<>( productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        log.info("Find product by id: {}", id);
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/product-image")
    public ResponseEntity<ProductImage> addProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Product product = productService.getProduct(id);
        ProductImage productImage = productImageStorageService.createImage(multipartFile);
        productImage = productImageService.createProductImage(product, productImage);
        return new ResponseEntity<>(productImage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/product-image")
    public ResponseEntity<List<ImageItem>> getAllProductImage(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        List<ImageItem> imageItems = productImageStorageService.getAllProductImages(product);
        return new ResponseEntity<>(imageItems, HttpStatus.OK);
    }

}
