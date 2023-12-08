package ru.sfu.zooshopback.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.repository.ProductRepository;
import ru.sfu.zooshopback.service.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    Category category = new Category(1L, "Category 1", "category-1");

    Product RECORD_1 = new Product(1L, category, "name 1", "description 1", new BigDecimal("10000"), 100);
    Product RECORD_2 = new Product(2L, category, "name 2", "description 2", new BigDecimal("11000"), 250);
    Product RECORD_3 = new Product(3L, category, "name 3", "description 3", new BigDecimal("15000"), 300);

    @Test
    void findAll_ReturnedValidListOfProduct() {
        List<Product> productList = List.of(RECORD_1, RECORD_2, RECORD_3);
        Mockito.doReturn(productList).when(this.productRepository).findAll();

        List<Product> returned = productService.findAll();

        assertEquals(productList, returned);
    }

    @Test
    void getProduct_ReturnedValidProduct() {
        Optional<Product> optionalProduct = Optional.of(RECORD_1);
        Mockito.doReturn(optionalProduct).when(this.productRepository).findById(1L);

        Product product = productService.getProduct(1L);

        assertEquals(product, RECORD_1);
    }

    @Test
    void getProduct_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<Product> optionalProduct = Optional.empty();

        Mockito.doReturn(optionalProduct).when(this.productRepository).findById(1L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProduct(1L);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void createProduct_ReturnValidProduct() {
        Optional<Product> productOptional = Optional.empty();

        Mockito.doReturn(productOptional).when(this.productRepository).findByName(RECORD_1.getName());

        Product product = productService.createProduct(RECORD_1);

        assertEquals(product, RECORD_1);
    }

    @Test
    void createProduct_ThrowResourceAlreadyExistsException_whenNameAlreadyExist() {
        Optional<Product> productOptional = Optional.of(RECORD_1);

        Mockito.doReturn(productOptional).when(this.productRepository).findByName(RECORD_1.getName());

        Throwable throwable = assertThrows(ResourceAlreadyExistsException.class, () -> {
            productService.createProduct(RECORD_1);
        });
        assertNotNull(throwable.getMessage());
    }

    @Test
    void deleteProduct_ReturnValidProduct() {
        Optional<Product> optionalProduct = Optional.of(RECORD_1);

        Mockito.doReturn(optionalProduct).when(this.productRepository).findById(1L);

        Product product = productService.deleteProduct(1L);

        assertEquals(product, RECORD_1);
    }

    @Test
    void deleteProduct_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<Product> optionalProduct = Optional.empty();

        Mockito.doReturn(optionalProduct).when(this.productRepository).findById(1L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertNotNull(throwable.getMessage());

    }

    @Test
    void updateProduct_ReturnValidProduct() {
        Optional<Product> optionalProductId = Optional.of(RECORD_2);
        Optional<Product> optionalProductName = Optional.empty();
        Product updatedProduct = new Product(2L, category, "name 245", "description 232", new BigDecimal("11000"), 250);

        Mockito.doReturn(optionalProductId).when(productRepository).findById(2L);
        Mockito.doReturn(optionalProductName).when(productRepository).findByName(updatedProduct.getName());

        Product product = productService.updateProduct(2L, updatedProduct);

        assertNotNull(product);
        assertEquals(updatedProduct.getName(), product.getName());
        assertEquals(updatedProduct.getId(), product.getId());
        assertEquals(updatedProduct.getPrice(), product.getPrice());
        assertEquals(updatedProduct.getQuantity(), product.getQuantity());
        assertEquals(updatedProduct.getDescription(), product.getDescription());
        assertEquals(updatedProduct.getCategoryId(), product.getCategoryId());

    }

    @Test
    void updateProduct_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<Product> optionalProductId = Optional.empty();

        Mockito.doReturn(optionalProductId).when(this.productRepository).findById(1L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1L, RECORD_3);
        });

        assertNotNull(throwable);
    }

    @Test
    void updateProduct_ThrowResourceAlreadyExistsException_whenNameAlreadyExist() {
        Optional<Product> optionalProductId = Optional.of(RECORD_2);
        Optional<Product> optionalProductName = Optional.of(RECORD_3);

        Mockito.doReturn(optionalProductId).when(productRepository).findById(2L);
        Mockito.doReturn(optionalProductName).when(productRepository).findByName(RECORD_1.getName());

        Throwable throwable = assertThrows(ResourceAlreadyExistsException.class, () -> {
            productService.updateProduct(2L, RECORD_1);
        });
        assertNotNull(throwable.getMessage());
    }
}




