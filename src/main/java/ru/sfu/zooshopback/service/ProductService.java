package ru.sfu.zooshopback.service;

import ru.sfu.zooshopback.service.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Product deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new ResourceNotFoundException("Product not found with id " + id);
        productRepository.delete(productOptional.get());
        return productOptional.get();
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new ResourceNotFoundException("Product not found with id " + id);
        Optional<Product> productOptionalFromName = productRepository.findByName(product.getName());
        if (productOptionalFromName.isPresent())
            throw new ResourceAlreadyExistsException("Product already exists with name " + product.getName());

        return setProduct(product, productOptional.get());
    }

    private static Product setProduct(Product product, Product productFromData) {
        if (product.getName() != null && !product.getName().isEmpty())
            productFromData.setName(product.getName());

        if (product.getDescription() != null && !product.getDescription().isEmpty())
            productFromData.setDescription(product.getDescription());

        if (product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) > 0)
            productFromData.setPrice(product.getPrice());

        if (product.getQuantity() != null && product.getQuantity() > 0)
            productFromData.setQuantity(product.getQuantity());
        return productFromData;
    }

}
