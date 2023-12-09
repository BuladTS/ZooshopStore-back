package ru.sfu.zooshopback.controller;


import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshopback.model.Comment;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshopback.DTO.ImageItem;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductImageStorageService productImageStorageService;
    private final RatingService ratingService;
    private final CommentService commentService;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    @Autowired
    public ProductController(
            ProductService productService,
            ProductImageService productImageService,
            ProductImageStorageService productImageStorageService,
            RatingService ratingService,
            CommentService commentService
    ) {
        this.productService = productService;
        this.productImageService = productImageService;
        this.productImageStorageService = productImageStorageService;
        headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        log.info("Find all products");
        return new ResponseEntity<>(productService.findAll(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        log.info("Find product by id: {}", id);
        return new ResponseEntity<>(productService.getProduct(id), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProduct(id), headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateById(@PathVariable Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/product-image")
    public ResponseEntity<ProductImage> addProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Product product = productService.getProduct(id);
        ProductImage productImage = productImageStorageService.createImage(multipartFile);
        productImage = productImageService.createProductImage(product, productImage);
        return new ResponseEntity<>(productImage, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/product-image")
    public ResponseEntity<List<ImageItem>> getAllProductImage(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        List<ImageItem> imageItems = productImageStorageService.getAllProductImages(product);
        return new ResponseEntity<>(imageItems, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable Long id) {
        Double rating = ratingService.getProductRating(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getAllProductComments(@PathVariable Long id) {
        List<Comment> comments = commentService.getAllProductComments(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
