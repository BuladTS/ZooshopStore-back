package com.example.zooshopback.service;

import com.example.zooshopback.model.Category;
import com.example.zooshopback.model.Product;
import com.example.zooshopback.repository.CategoryRepository;
import com.example.zooshopback.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> product =  productRepository.findById(id);
        return product.orElse(null);
    }

    public Product createProduct(Product product) {
        Optional<Product> productOptional = productRepository.findByName(product.getName());
        if (productOptional.isPresent())
            throw new IllegalArgumentException("Product already exists");
        productRepository.save(product);
        return product;
    }

    public Category getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }
}
