package com.example.zooshopback.service;

import com.example.zooshopback.exception.ResourceAlreadyExistsException;
import com.example.zooshopback.exception.ResourceNotFoundException;
import com.example.zooshopback.model.Product;
import com.example.zooshopback.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + id)
        );
    }

    public Product createProduct(Product product) {
        Optional<Product> productOptional = productRepository.findByName(product.getName());
        if (productOptional.isPresent())
            throw new ResourceAlreadyExistsException("Product already exists with name " + product.getName());
        productRepository.save(product);
        return product;
    }

}
