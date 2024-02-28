package ru.sfu.zooshopback.service;

import com.github.slugify.Slugify;
import ru.sfu.zooshopback.service.exception.ResourceAlreadyExistsException;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;
import ru.sfu.zooshopback.model.Category;
import ru.sfu.zooshopback.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final Slugify slg = Slugify.builder().transliterator(true).build();

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

    public Category deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ResourceNotFoundException("Could not find category with id: " + id);
        categoryRepository.delete(categoryOptional.get());
        return categoryOptional.get();
    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ResourceNotFoundException("Could not find category with id: " + id);
        Optional<Category> categoryOptionalFindByName = categoryRepository.findByName(category.getName());
        if (categoryOptionalFindByName.isPresent())
            throw new ResourceAlreadyExistsException("Category with name: " + category.getName() + " already exists");

        Category categoryFromDataBase = categoryOptional.get();
        if (category.getName() != null && !category.getName().isEmpty()) {
            categoryFromDataBase.setName(category.getName());
            categoryFromDataBase.setSlug(category.getSlug());
        }
        return categoryFromDataBase;
    }

    public Category createCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
        if (categoryOptional.isPresent())
            throw new ResourceAlreadyExistsException("Category already exists with name: " + category.getName());
        String slug = slg.slugify(category.getName());
        category.setSlug(slug);
        categoryRepository.save(category);
        return category;
    }
}
