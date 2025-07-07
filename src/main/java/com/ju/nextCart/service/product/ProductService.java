package com.ju.nextCart.service.product;

import com.ju.nextCart.exceptions.ResourceNotFoundException;
import com.ju.nextCart.model.Category;
import com.ju.nextCart.model.Product;
import com.ju.nextCart.repository.CategoryRepository;
import com.ju.nextCart.repository.ProductRepository;
import com.ju.nextCart.request.AddProductRequest;
import com.ju.nextCart.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(AddProductRequest product) {
        //check if the category is found in the db
        //If yes, set it as new product, no need to create category again
        //Else, save as new category and then set it as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName())).orElseGet(()->{
            Category newCategory = new Category(product.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));
    }

    private Product createProduct(AddProductRequest productRequest, Category category) {
        return new Product (
                productRequest.getName(),
                productRequest.getBrand(),
                productRequest.getPrice(),
                productRequest.getInventory(),
                productRequest.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(productRepository::delete,
                ()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProductById(ProductUpdateRequest updateProduct, Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    Product updated = updateExistingProducts(product, updateProduct);
                    return productRepository.save(updated);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProducts(Product product, ProductUpdateRequest request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);
        return product;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
