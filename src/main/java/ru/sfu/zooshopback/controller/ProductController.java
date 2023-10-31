package ru.sfu.zooshopback.controller;


import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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

//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        log.info("Create product: {}", product);
//        if (product.getCategory() == null) {
//            log.error("Category is null");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Product productServiceProduct = productService.createProduct(product);
//        return productServiceProduct!= null ?
//                new ResponseEntity<>(productServiceProduct, HttpStatus.CREATED)
//                : new ResponseEntity<>(HttpStatus.CONFLICT);
//    }

//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody PostProduct postProduct) {
//        log.info("Create product: categoryId={}, name={}, slug={}, description={}, price={}, quantity={}", postProduct.getCategoryId(), postProduct.getName(), postProduct.getSlug(), postProduct.getDescription(), postProduct.getPrice(), postProduct.getQuantity());
//        Category category = productService.getCategory(postProduct.getCategoryId());
//        if (category == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);
//        Product product = new Product(category, name, slug, description, price, quantity);
//        product = productService.createProduct(product);
//        return product != null ?
//                new ResponseEntity<>(product, HttpStatus.CREATED)
//                : new ResponseEntity<>(HttpStatus.CONFLICT);
//    }
}
