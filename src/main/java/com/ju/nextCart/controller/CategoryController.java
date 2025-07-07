package com.ju.nextCart.controller;

import com.ju.nextCart.exceptions.ResourceNotFoundException;
import com.ju.nextCart.model.Category;
import com.ju.nextCart.response.ApiResponse;
import com.ju.nextCart.service.category.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add-category-by-name")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/get-category-by-id/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/get-category-by-name/{name}")
    public ResponseEntity<ApiResponse> getAllCategoryById(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete-category-by-id/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Delete success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update-category-by-id/{id}")
    public ResponseEntity<ApiResponse> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updateCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
