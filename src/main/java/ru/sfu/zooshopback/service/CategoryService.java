package ru.sfu.zooshopback.service;

import ru.sfu.zooshopback.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.exception.ResourceNotFoundException;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Could not find category with id: " + id)
        );
    }

    public Category createCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
        if (categoryOptional.isPresent())
            throw new ResourceAlreadyExistsException("Category already exists with name: " + category.getName());
        categoryRepository.save(category);
        return category;
    }
}
