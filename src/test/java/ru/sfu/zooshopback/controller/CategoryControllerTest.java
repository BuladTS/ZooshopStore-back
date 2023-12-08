package ru.sfu.zooshopback.controller;

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
import ru.sfu.zooshopback.service.CategoryService;
import ru.sfu.zooshopback.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;
    @InjectMocks
    private CategoryController categoryController;

    Category RECORD_1 = new Category(1L, "Category 1", "category-1");
    Category RECORD_2 = new Category(2L, "Category 2", "category-2");
    Category RECORD_3 = new Category(3L, "Category 3", "category-3");


    @Test
    void findAll_ReturnsValidResponseEntity() {
        List<Category> categoryList = List.of(RECORD_1, RECORD_2, RECORD_3);
        Mockito.doReturn(categoryList).when(this.categoryService).findAll();

        ResponseEntity<List<Category>> response = categoryController.findAll();
//        ResponseEntity<List<Category>> response = null;

//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(categoryList, response.getBody());
    }

    @Test
    void findById_ReturnsValidResponseEntity() {
        Mockito.doReturn(RECORD_1).when(this.categoryService).getCategory(1L);

        ResponseEntity<Category> response = categoryController.findById(1L);

        assertEquals(RECORD_1, response.getBody());
    }

    @Test
    void deleteById_ReturnsValidResponseEntity() {
        Mockito.doReturn(RECORD_1).when(this.categoryService).deleteCategory(1L);

        ResponseEntity<Category> response = categoryController.deleteById(1L);

        assertEquals(RECORD_1, response.getBody());

    }

    @Test
    void updateById_ReturnsValidResponseEntity() {
        Category RECORD_4 = new Category(1L, "Category 4", "category-4");

        Mockito.doReturn(RECORD_4).when(this.categoryService).updateCategory(1L, RECORD_4);

        ResponseEntity<Category> response = categoryController.updateById(1L, RECORD_4);

        assertEquals(RECORD_4, response.getBody());
    }

    @Test
    void createCategory_ReturnsValidResponseEntity() {
        Category RECORD_5 = new Category(1L, "Category 5", "category-5");

        Mockito.doReturn(RECORD_5).when(this.categoryService).createCategory(RECORD_5);

        ResponseEntity<Category> response = categoryController.createCategory(RECORD_5);


        assertEquals(RECORD_5, response.getBody());
    }

    @Test
    void addProduct_ReturnsValidResponseEntity() {
        Product PRODUCT_WITHOUT_ID = new Product(RECORD_1, "Product 1", "product-1", new BigDecimal(1000), 100);
        Product PRODUCT_WITH_ID = new Product(1L ,RECORD_1, "Product 1", "product-1", new BigDecimal(1000), 100);

        Mockito.doReturn(RECORD_1).when(this.categoryService).getCategory(1L);
        Mockito.doReturn(PRODUCT_WITH_ID).when(this.productService).createProduct(PRODUCT_WITHOUT_ID);

        ResponseEntity<?> response = categoryController.addProduct(1L, PRODUCT_WITHOUT_ID);


        assertEquals(PRODUCT_WITH_ID, response.getBody());
    }
}
