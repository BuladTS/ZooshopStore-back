package ru.sfu.zooshopback.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.security.core.parameters.P;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.repository.ProductImageRepository;
import ru.sfu.zooshopback.service.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private Environment environment;

    @InjectMocks
    private ProductImageService productImageService;

    Category category = new Category(1L, "Category 1", "category-1");

    Product product = new Product(1L, category, "name 1", "description 1", new BigDecimal("10000"), 100);

    ProductImage RECORD_1 = new ProductImage(1L, product, "name.jpeg", "api/product-image/name.jpeg");
    ProductImage RECORD_2 = new ProductImage(2L, product, "name1.jpeg", "api/product-image/name1.jpeg");

    @Test
    void findAll_ReturnsValidListProductImage() {
        List<ProductImage> productImageList = List.of(
                RECORD_1, RECORD_2
        );

        Mockito.doReturn(productImageList).when(this.productImageRepository).findAll();

        List<ProductImage> returnedList = productImageService.findAll();

        assertEquals(productImageList, returnedList);
    }

    // TODO: Работа с файлами
    @Test
    void findById_ReturnsValidProductImage() {
        Optional<ProductImage> optionalProductImage = Optional.of(RECORD_1);

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findById(RECORD_1.getId());
        Mockito.doReturn("/media/images/").when(this.environment).getProperty("local.upload.image");

        Resource resource = productImageService.findById(1L);

        assertEquals(RECORD_1.getName(), resource.getFilename());

    }

    @Test
    void findById_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<ProductImage> optionalProductImage = Optional.empty();

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findById(RECORD_1.getId());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            productImageService.findById(1L);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void existProductImageWithName_ReturnsValidFalse() {
        Optional<ProductImage> optionalProductImage = Optional.empty();

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());

        boolean exist = productImageService.existProductImageWithName(RECORD_1.getName());

        assertFalse(exist);
    }

    @Test
    void existProductImageWithName_ReturnsValidTrue() {
        Optional<ProductImage> optionalProductImage = Optional.of(RECORD_1);

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());

        boolean exist = productImageService.existProductImageWithName(RECORD_1.getName());

        assertTrue(exist);
    }


    // TODO: Работа с файлами
    @Test
    void findByName_ReturnsValidResource() {
        Optional<ProductImage> optionalProductImage = Optional.of(RECORD_1);

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());
        Mockito.doReturn("/media/images/").when(this.environment).getProperty("local.upload.image");

        Resource resource = productImageService.findByName(RECORD_1.getName());

        assertEquals(RECORD_1.getName(), resource.getFilename());
    }

    @Test
    void findByName_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<ProductImage> optionalProductImage = Optional.empty();

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            productImageService.findByName(RECORD_1.getName());
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void createProductImage_ReturnsValidProductImage() {
        Optional<ProductImage> optionalProductImage = Optional.empty();
        ProductImage productImageWithOutProduct = new ProductImage(1L, "name.jpeg", "api/product-image/name.jpeg");

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());

        ProductImage productImage = productImageService.createProductImage(product, productImageWithOutProduct);

        assertEquals(RECORD_1, productImage);
    }

    @Test
    void createProductImage_ResourceAlreadyExistsException_whenNameAlreadyExist() {
        Optional<ProductImage> optionalProductImage = Optional.of(RECORD_1);

        Mockito.doReturn(optionalProductImage).when(this.productImageRepository).findByName(RECORD_1.getName());

        Throwable throwable = assertThrows(ResourceAlreadyExistsException.class, () -> {
            productImageService.createProductImage(product, RECORD_1);
        });

        assertNotNull(throwable.getMessage());
    }
}
