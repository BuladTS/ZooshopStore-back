package ru.sfu.zooshopback.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product controller", description = "Позволяет производить операции с продуктами")
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

    @Operation(
            summary = "Получение всех продуктов",
            description = "Позволяет получить все продукты"
    )
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        log.info("Find all products");
        return new ResponseEntity<>(productService.findAll(), headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение определенного продукта",
            description = "Позволяет получить определенный продукт"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        log.info("Find product by id: {}", id);
        return new ResponseEntity<>(productService.getProduct(id), headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление определенного продукта",
            description = "Позволяет удалить определенный продукт"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProduct(id), headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Изменение определенного продукта",
            description = "Позволяет изменить определенный продукт"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateById(@PathVariable Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление нового изображения к определенному продукту",
            description = "Позволяет добавлять новое изображение к определенному продукту"
    )
    @PostMapping("/{id}/product-image")
    public ResponseEntity<ProductImage> addProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Product product = productService.getProduct(id);
        ProductImage productImage = productImageStorageService.createImage(multipartFile);
        productImage = productImageService.createProductImage(product, productImage);
        return new ResponseEntity<>(productImage, headers, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Получение всех изображений продукта",
            description = "Позволяет получить все изображения продукта"
    )
    @GetMapping("/{id}/product-image")
    public ResponseEntity<List<ImageItem>> getAllProductImage(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        List<ImageItem> imageItems = productImageStorageService.getAllProductImages(product);
        return new ResponseEntity<>(imageItems, headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение райтинга продукта",
            description = "Позволяет получить рейтинг продукта"
    )
    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable Long id) {
        Double rating = ratingService.getProductRating(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение коментариев продукта",
            description = "Позволяет получить коментарии продукта"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getAllProductComments(@PathVariable Long id) {
        List<Comment> comments = commentService.getAllProductComments(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
