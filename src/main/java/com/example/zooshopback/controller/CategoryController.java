package com.example.zooshopback.controller;

import com.example.zooshopback.model.Category;
import com.example.zooshopback.model.Product;
import com.example.zooshopback.service.CategoryService;
import com.example.zooshopback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<?> addProduct(@PathVariable Long id, @RequestBody Product product) {
        Category category = categoryService.getCategory(id);
        product.setCategory(category);
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

}
