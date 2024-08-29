package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.ProductCategory;
import com.springboot.stockmanagementhub.model.dto.ErrorResponse;
import com.springboot.stockmanagementhub.model.dto.ProductCategoryDTO;
import com.springboot.stockmanagementhub.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("api")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductCategory>> getAllProductCategory() {
        return ResponseEntity.ok().body(productCategoryService.getAllProductCategory());
    }

    @GetMapping("/v2/category")
    public ResponseEntity<List<ProductCategory>> getAllProductCategory2(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize) {
        Page<ProductCategory> category = productCategoryService.getAllProductCategory2(pageNo, pageSize);
        return ResponseEntity.ok(category.getContent());
    }

    @GetMapping("/v3/category")
    public ResponseEntity<List<ProductCategory>> getProductCategory3(Pageable pageable) {
        Page<ProductCategory> category = productCategoryService.getAllProductCategory3(pageable);
        return ResponseEntity.ok(category.getContent());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductCategoryByID(@PathVariable Integer id) {
        log.info("Get product category id by " + id);
        if (id < 1) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Product category id cannot be less than 1",
                            "Invalid ID",
                            List.of("ID must be greater than 0"))
            );
        }
        return productCategoryService.getProductCategoryById(Long.valueOf(id))
                                     .map(productCategory -> ResponseEntity.ok().body(productCategory))
                                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/category")
    public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory) {
        log.info("Request to create product category => {}", productCategory);
        if (productCategory.getId() != null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID should be null",
                            "Invalid ID",
                            List.of("ID should be null when creating a new product category"))
            );
        }
        return ResponseEntity.ok().body(productCategoryService.createProductCategory(productCategory));
    }

    @PostMapping("/v2/category")
    public ResponseEntity<?> createProductCategory(@Valid @RequestBody ProductCategoryDTO createProductCategoryDTO) {
        log.info("Request to create product category v2 => {}", createProductCategoryDTO);
        return ResponseEntity.ok().body(productCategoryService.createProductCategoryV2(createProductCategoryDTO));
    }

    @PutMapping("/category")
    public ResponseEntity<?> updateProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory.getId() == null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be null",
                            "Invalid ID",
                            List.of("ID must be provided for updates"))
            );
        }
        Optional<ProductCategory> updatedProductCategory = productCategoryService.updateProductCategory(productCategory);
        if (updatedProductCategory.isPresent()) {
            return ResponseEntity.ok(updatedProductCategory);
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Product category not found",
                            "Update failed",
                            List.of("Product category with the given ID doesn't exist"))
            );
        }
    }

    @PutMapping("/v2/category/{id}")
    public ResponseEntity<?> updateProductCategoryV2(@Valid @RequestBody ProductCategoryDTO updateProductCategoryDTO, @PathVariable Long id) {
        log.info("Incoming request to update product category v2 with id {} and payload {}", id, updateProductCategoryDTO);
        ProductCategory updatedProductCategoryV2 = productCategoryService.updateProductCategoryV2(id, updateProductCategoryDTO);
        return ResponseEntity.ok(updatedProductCategoryV2);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Product category id cannot be less than 1",
                            "Invalid ID",
                            List.of("ID must be greater than 0"))
            );
        }
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }

}
