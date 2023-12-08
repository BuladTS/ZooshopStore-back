package ru.sfu.zooshopback.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.repository.CategoryRepository;
import ru.sfu.zooshopback.service.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    Category RECORD_1 = new Category(1L, "Category 1", "category-1");
    Category RECORD_2 = new Category(2L, "Category 2", "category-2");
    Category RECORD_3 = new Category(3L, "Category 3", "category-3");

    @Test
    void findAll_ReturnsValidCategoryList() {

        List<Category> categoryList = List.of(
                RECORD_1, RECORD_2, RECORD_3
        );

        Mockito.doReturn(categoryList).when(this.categoryRepository).findAll();

        List<Category> returnedList = categoryService.findAll();

        assertEquals(categoryList, returnedList);
    }

    @Test
    void getCategory_ReturnsValidCategory() {

        Optional<Category> optionalCategory = Optional.of(RECORD_1);

        Mockito.doReturn(optionalCategory).when(this.categoryRepository).findById(1L);

        Category returned = categoryService.getCategory(1L);

        assertEquals(RECORD_1, returned);
    }

    @Test
    void getCategory_ThrowResourceNotFoundException_whenResourceNotFound() {

        Optional<Category> optionalCategory = Optional.empty();

        Mockito.doReturn(optionalCategory).when(this.categoryRepository).findById(123L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategory(123L);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void deleteCategory_ReturnsValidCategory() {

        Optional<Category> optionalCategory = Optional.of(RECORD_1);

        Mockito.doReturn(optionalCategory).when(this.categoryRepository).findById(1L);

        Category returned = categoryService.deleteCategory(1L);


        assertEquals(RECORD_1, returned);

    }

    @Test
    void deleteCategory_ThrowResourceNotFoundException_whenResourceNotFound() {

        Optional<Category> optionalCategory = Optional.empty();

        Mockito.doReturn(optionalCategory).when(this.categoryRepository).findById(123L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(123L);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void updateCategory_ReturnsValidCategory() {
        Category updatedCategory = new Category(1L, "category 124", "category-124");
        Optional<Category> categoryOptional = Optional.of(RECORD_1);
        Optional<Category> categoryForCheckName = Optional.empty();

        Mockito.doReturn(categoryOptional).when(categoryRepository).findById(1L);
        Mockito.doReturn(categoryForCheckName).when(categoryRepository).findByName(updatedCategory.getName());

        Category returned = categoryService.updateCategory(1L, updatedCategory);


        assertEquals(updatedCategory, returned);
    }

    @Test
    void updateCategory_ThrowResourceNotFoundException_whenResourceNotFound() {
        Optional<Category> optionalCategory = Optional.empty();

        Mockito.doReturn(optionalCategory).when(this.categoryRepository).findById(123L);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(123L, RECORD_3);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void updateCategory_ResourceAlreadyExistsException_whenNameAlreadyExist() {
        Category updatedCategory = new Category(1L, "category 2", "category-2");
        Optional<Category> categoryOptional = Optional.of(RECORD_1);
        Optional<Category> categoryForCheckName = Optional.of(RECORD_2);

        Mockito.doReturn(categoryOptional).when(this.categoryRepository).findById(1L);
        Mockito.doReturn(categoryForCheckName).when(this.categoryRepository).findByName(updatedCategory.getName());

        Throwable throwable = assertThrows(ResourceAlreadyExistsException.class, () -> {
            categoryService.updateCategory(1L, updatedCategory);
        });

        assertNotNull(throwable.getMessage());
    }

    @Test
    void createCategory_ReturnsValidCategory() {
        Optional<Category> categoryWithName = Optional.empty();

        Mockito.doReturn(categoryWithName).when(categoryRepository).findByName(RECORD_1.getName());

        Category returned = categoryService.createCategory(RECORD_1);


        assertEquals(RECORD_1, returned);
    }

    @Test
    void createCategory_ResourceAlreadyExistsException_whenNameAlreadyExist() {
        Optional<Category> categoryOptional = Optional.of(RECORD_1);

        Mockito.doReturn(categoryOptional).when(categoryRepository).findByName(RECORD_1.getName());

        Throwable throwable = assertThrows(ResourceAlreadyExistsException.class, () -> {
            categoryService.createCategory(RECORD_1);
        });

        assertNotNull(throwable.getMessage());
    }


}
