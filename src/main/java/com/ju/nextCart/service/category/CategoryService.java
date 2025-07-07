package com.ju.nextCart.service.category;

import com.ju.nextCart.exceptions.AlreadyExistsException;
import com.ju.nextCart.exceptions.ResourceNotFoundException;
import com.ju.nextCart.model.Category;
import com.ju.nextCart.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(
                c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save).orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists!"));
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(existingCategory -> {
                        existingCategory.setName(category.getName());
                        return categoryRepository.save(existingCategory);
                })
                .orElseThrow(()->new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository::delete, () ->{
                    throw new ResourceNotFoundException("Category not found!");
                });
    }
}
