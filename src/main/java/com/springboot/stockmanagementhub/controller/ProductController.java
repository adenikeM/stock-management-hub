package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.Product;
import com.springboot.stockmanagementhub.model.Sales;
import com.springboot.stockmanagementhub.model.dto.*;
import com.springboot.stockmanagementhub.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("api")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok().body(productService.getAllProduct());
    }

    @GetMapping("/v2/products")
    public ResponseEntity<List<Product>> getAllProduct2(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize) {
        Page<Product> products = productService.getAllProduct2(pageNo, pageSize);
        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/v3/products")
    public ResponseEntity<List<Product>> getAllProductWithPageable(Pageable pageable) {
        Page<Product> products = productService.getAllProduct3(pageable);
        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductByFilter(@RequestParam(defaultValue = "") String name,
                                                               @RequestParam(defaultValue = "") String colour,
                                                               @RequestParam(defaultValue = "") String size,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "1") int pageSize) {
        Page<Product> products = productService.searchProductByFilter(name, colour, size, page, pageSize);
        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductByID(@PathVariable Long id) {
        log.info("Get product id by " + id);
        if (id < 1) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Product id cannot be less than 1",
                            "Invalid ID",
                            List.of("ID must be greater than 0"))
            );
        }
        return productService.getProductById(id)
                             .map(product -> ResponseEntity.ok().body(product))
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        log.info("Request to create product => {}", product);
        if (product.getId() != null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID should be null",
                            "Invalid ID",
                            List.of("ID should be null when creating a new product"))
            );
        }
        return ResponseEntity.ok().body(productService.createProduct(product));
    }

    @PostMapping("/v2/products")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO createProductDTO) {
        log.info("Request to create product v2 => {}", createProductDTO);
        return ResponseEntity.ok().body(productService.createProductV2(createProductDTO));
    }

    @PutMapping("/products")
    @PreAuthorize("hasAuthority('MANAGER') and hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be null",
                            "Invalid ID",
                            List.of("ID must be provided for updates"))
            );
        }
        Optional<Product> updatedProduct = productService.updateProduct(product);
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Product not found",
                            "Update failed",
                            List.of("Product with the given ID doesn't exist"))
            );
        }
    }

    @PutMapping("/v2/products/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<?> updateProductV2(@Valid @RequestBody ProductDTO updateProductDTO, @PathVariable Long id) {
        log.info("Incoming request to update product v2 with id {} and payload {}", id, updateProductDTO);
        Product updatedProductV2 = productService.updateProductV2(id, updateProductDTO);
        return ResponseEntity.ok(updatedProductV2);
    }

    @PostMapping("/products/increase")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<?> increaseStock(@RequestBody List<UpdateStockDTO> updateStockDTOS) {
        log.info("Request to increase stock => {}", updateStockDTOS);
        return ResponseEntity.ok(productService.increaseStock(updateStockDTOS));
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products/price")
    public ResponseEntity<ProductPriceDTO> getProductPrice(@RequestBody List<ProductsToBePriced> productsToBePriced) {
        log.info("Incoming request to get product price with ids {}", productsToBePriced);
        ProductPriceDTO productPriceDTO = productService.getProductPriceV2(productsToBePriced);
        log.info("Response for product price {}", productPriceDTO);
        return ResponseEntity.ok(productPriceDTO);
    }

    @PostMapping("/products/sell")
    @PreAuthorize("hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<Sales> sellProducts(@Valid @RequestBody SellProductsDTO sellProductsDTO) {
        log.info("Incoming request to sell products {}", sellProductsDTO);
        Sales sales = productService.sellProduct(sellProductsDTO);
        log.info("Response for sell product {}", sales);
        return ResponseEntity.ok(sales);
    }
}
