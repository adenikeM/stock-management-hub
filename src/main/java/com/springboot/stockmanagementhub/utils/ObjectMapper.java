package com.springboot.stockmanagementhub.utils;

import com.springboot.stockmanagementhub.model.Location;
import com.springboot.stockmanagementhub.model.Product;
import com.springboot.stockmanagementhub.model.ProductCategory;
import com.springboot.stockmanagementhub.model.dto.ProductCategoryDTO;
import com.springboot.stockmanagementhub.model.dto.ProductDTO;

public class ObjectMapper {
    public static Product mapCreateProductDTOToProduct(ProductDTO createProductDTO, ProductCategory productCategory) {
        Product product = new Product();
        product.setName(createProductDTO.getName());
        product.setColour(createProductDTO.getColour());
        product.setPrice(createProductDTO.getPrice());
        product.setExpiryDate(createProductDTO.getExpiryDate());
        product.setAvailableQuantity(createProductDTO.getAvailableQuantity());
        product.setManufactureDate(createProductDTO.getManufactureDate());
        product.setSize(createProductDTO.getSize());
        product.setProductCategory(productCategory);
        return product;
    }

    public static void mapUpdateProductDTOToProduct(ProductDTO updateProductDTO , ProductCategory productCategory, Product retrievedProduct) {
        retrievedProduct.setName(updateProductDTO .getName());
        retrievedProduct.setColour(updateProductDTO .getColour());
        retrievedProduct.setPrice(updateProductDTO .getPrice());
        retrievedProduct.setExpiryDate(updateProductDTO .getExpiryDate());
        retrievedProduct.setAvailableQuantity(updateProductDTO .getAvailableQuantity());
        retrievedProduct.setManufactureDate(updateProductDTO .getManufactureDate());
        retrievedProduct.setSize(updateProductDTO .getSize());
        retrievedProduct.setProductCategory(productCategory);
    }
    public static ProductCategory mapCreateProductCategoryDTOToProductCategory(ProductCategoryDTO createProductCategoryDTO, Location location) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(createProductCategoryDTO.getCategoryName());
        productCategory.setCreatedDate(createProductCategoryDTO.getCreatedDate());
        productCategory.setLocation(location);
        return productCategory;
    }
    public static void mapUpdateProductCategoryDTOToProductCategory(ProductCategoryDTO updateProductCategoryDTO , Location location, ProductCategory retrievedProductCategory) {
        retrievedProductCategory.setCategoryName(updateProductCategoryDTO.getCategoryName());
        retrievedProductCategory.setCreatedDate(updateProductCategoryDTO .getCreatedDate());
        retrievedProductCategory.setLocation(location);
    }
}
