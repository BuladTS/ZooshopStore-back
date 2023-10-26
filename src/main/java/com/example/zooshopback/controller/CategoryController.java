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
        Category category =  categoryService.getCategory(id);
        return category != null ?
                new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category categoryServiceCategory = categoryService.createCategory(category);
        return categoryServiceCategory != null ?
                new ResponseEntity<>(categoryServiceCategory, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<Product> addProduct(@PathVariable Long id, @RequestBody Product product) {
        Category category = categoryService.getCategory(id);
        if (category == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        product.setCategory(category);
        product = productService.createProduct(product);
        return product != null ?
                new ResponseEntity<>(product, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
