package com.ju.nextCart.controller;

import com.ju.nextCart.exceptions.ResourceNotFoundException;
import com.ju.nextCart.model.Product;
import com.ju.nextCart.request.AddProductRequest;
import com.ju.nextCart.request.ProductUpdateRequest;
import com.ju.nextCart.response.ApiResponse;
import com.ju.nextCart.service.product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        return ResponseEntity.ok(new ApiResponse("Success!", productService.getAllProducts()));
    }

    @GetMapping("/get-products-by-id")
    public ResponseEntity<ApiResponse> getProductById(@RequestParam(value="productId", required=true) Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create-product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest product) {
        try {
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Create Success!", product1));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update-product")
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam(value="productId", required=true) Long id, @RequestBody ProductUpdateRequest product) {
        try {
            Product product1 = productService.updateProductById(product, id);
            return ResponseEntity.ok(new ApiResponse("Update Success!", product1));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam(value="productId", required=true) Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/get-products-by-brandAndName")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam(value="brandName", required = true) String brandName, @RequestParam(value= "productName", required = true) String productName) {
        try {
            List<Product> product = productService.getProductsByBrandAndName(brandName, productName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam(value="productName", required=true) String productName) {
        try {
            List<Product> product = productService.getProductsByName(productName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-categoryAndBrand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam(value= "categoryName", required = true) String categoryName, @RequestParam(value= "brandName", required = true) String brandName) {
        try {
            List<Product> product = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam(value= "brandName", required = true) String brandName) {
        try {
            List<Product> product = productService.getProductsByBrand(brandName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-products-by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam(value= "categoryName", required = true) String categoryName) {
        try {
            List<Product> product = productService.getProductsByCategory(categoryName);
            if(product.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-count-by-brandAndName")
    public ResponseEntity<ApiResponse> getCountByBrandAndName(@RequestParam(value="brandName", required=true) String brandName, @RequestParam(value="productName", required=true) String productName) {
        try {
            Long productCount= productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Success!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
