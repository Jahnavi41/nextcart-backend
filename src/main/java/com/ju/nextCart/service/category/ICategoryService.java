package com.ju.nextCart.service.category;

import com.ju.nextCart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategoryById(Long categoryId);
}
