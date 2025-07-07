package com.ju.nextCart.service.product;

import com.ju.nextCart.model.Product;
import com.ju.nextCart.request.AddProductRequest;
import com.ju.nextCart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long productId);
    void deleteProductById(Long productId);
    Product updateProductById(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
