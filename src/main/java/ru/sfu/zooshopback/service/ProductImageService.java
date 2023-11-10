package ru.sfu.zooshopback.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import ru.sfu.zooshopback.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.exception.ResourceNotFoundException;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final Environment environment;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository, Environment environment) {
        this.productImageRepository = productImageRepository;
        this.environment = environment;
    }

    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    public Resource findById(Long id) {
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if (productImage.isEmpty())
            throw new ResourceNotFoundException("Product image with id " + id + " not found");
        return new FileSystemResource(System.getProperty("user.dir") + environment.getProperty("local.upload.image") + productImage.get().getName());
    }

    public boolean existProductImageWithName(String name) {
        Optional<ProductImage> productImage = productImageRepository.findByName(name);
        return productImage.isPresent();
    }

    public Resource findByName(String name) {
        Optional<ProductImage> productImage = productImageRepository.findByName(name);
        if (productImage.isEmpty())
            throw new ResourceNotFoundException("Product image with name " + name + "not found");
        return new FileSystemResource(System.getProperty("user.dir") + environment.getProperty("local.upload.image") + productImage.get().getName());
    }

    public ProductImage createProductImage(Product product, ProductImage productImage) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findByName(productImage.getName());
        if (optionalProductImage.isPresent())
            throw new ResourceAlreadyExistsException("ProductImage with name " + productImage.getName() + " already exists");
        productImage.setProduct(product);
        productImageRepository.save(productImage);
        return productImage;
    }

}
