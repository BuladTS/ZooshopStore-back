package ru.sfu.zooshopback.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.model.Product;
import ru.sfu.zooshopback.model.ProductImage;
import ru.sfu.zooshopback.service.ProductImageService;
import ru.sfu.zooshopback.service.ProductImageStorageService;
import ru.sfu.zooshopback.service.ProductService;
import ru.sfu.zooshopback.DTO.ImageItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductImageService productImageService;

    @Mock
    private ProductImageStorageService productImageStorageService;

    @InjectMocks
    private ProductController productController;

    Category category = new Category(1L, "Category 1", "category-1");

    Product RECORD_1 = new Product(1L, category, "name 1", "description 1", new BigDecimal("10000"), 100);
    Product RECORD_2 = new Product(2L, category, "name 2", "description 2", new BigDecimal("11000"), 250);
    Product RECORD_3 = new Product(3L, category, "name 3", "description 3", new BigDecimal("15000"), 300);

    @Test
    void findAll_ReturnedValidResponseEntity() {
        List<Product> productList = List.of(RECORD_1, RECORD_2, RECORD_3);
        Mockito.doReturn(productList).when(this.productService).findAll();

        ResponseEntity<List<Product>> response = productController.findAll();

        assertEquals(productList, response.getBody());

    }

    @Test
    void findById_ReturnedValidResponseEntity() {
        Mockito.doReturn(RECORD_1).when(this.productService).getProduct(1L);

        ResponseEntity<Product> response = productController.findById(1L);

        assertEquals(RECORD_1, response.getBody());
    }

    @Test
    void deleteById_ReturnedValidResponseEntity() {
        Mockito.doReturn(RECORD_1).when(this.productService).deleteProduct(1L);

        ResponseEntity<Product> response = productController.deleteById(1L);


        assertEquals(RECORD_1, response.getBody());
    }

    @Test
    void updateById_ReturnedValidResponseEntity() {
        Product RECORD_4 = new Product(1L, category, "products1", "description yes", new BigDecimal(1000), 123);

        Mockito.doReturn(RECORD_4).when(this.productService).updateProduct(1L, RECORD_1);

        ResponseEntity<Product> response = productController.updateById(1L, RECORD_1);

        assertEquals(RECORD_4, response.getBody());
    }

    @Test
    void addProductImage_ReturnedValidResponseEntity() throws IOException {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "cast.jpg",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "Hello World".getBytes()
        );
        ProductImage productImageWithoutIdAndProduct = new ProductImage("cast.jpg", "api/product-image/cats.jpg");
        ProductImage productImageWithIdAndProduct = new ProductImage(1L, RECORD_1, "cast.jpg", "api/product-image/cats.jpg");

        Mockito.doReturn(RECORD_1).when(this.productService).getProduct(1L);
        Mockito.doReturn(productImageWithoutIdAndProduct).when(this.productImageStorageService).createImage(file);
        Mockito.doReturn(productImageWithIdAndProduct).when(this.productImageService).createProductImage(RECORD_1, productImageWithoutIdAndProduct);

        ResponseEntity<ProductImage> response = productController.addProductImage(1L, file);


        assertEquals(productImageWithIdAndProduct, response.getBody());
    }

    @Test
    void getAllProductImage_ReturnedValidResponseEntity() {
        List<ImageItem> imageItems = List.of(
                new ImageItem( "image.jpeg", "api/product-image/image.jpeg"),
                new ImageItem("image-1.jpeg", "api/product-image/image-1.jpeg")
        );

        Mockito.doReturn(RECORD_1).when(this.productService).getProduct(1L);
        Mockito.doReturn(imageItems).when(this.productImageStorageService).getAllProductImages(RECORD_1);

        ResponseEntity<List<ImageItem>> response = productController.getAllProductImage(1L);


        assertEquals(imageItems, response.getBody());
    }

}
