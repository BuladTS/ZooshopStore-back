package ru.sfu.zooshopback.controller;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.service.CategoryService;
import ru.sfu.zooshopback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.deleteCategory(id), headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateById(@PathVariable Long id, @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.updateCategory(id, category), headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.createCategory(category), headers, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<?> addProduct(@PathVariable Long id, @RequestBody Product product) {
        Category category = categoryService.getCategory(id);
        product.setCategory(category);
        return new ResponseEntity<>(productService.createProduct(product), headers, HttpStatus.CREATED);
    }

}
