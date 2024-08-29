package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.Location;
import com.springboot.stockmanagementhub.model.ProductCategory;
import com.springboot.stockmanagementhub.model.dto.ProductCategoryDTO;
import com.springboot.stockmanagementhub.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.springboot.stockmanagementhub.utils.ObjectMapper.mapCreateProductCategoryDTOToProductCategory;
import static com.springboot.stockmanagementhub.utils.ObjectMapper.mapUpdateProductCategoryDTOToProductCategory;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final LocationService locationService;


    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public Page<ProductCategory> getAllProductCategory2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productCategoryRepository.findAll(pageable);
    }
    public Page<ProductCategory> getAllProductCategory3(Pageable pageable){
        return productCategoryRepository.findAll(pageable);
    }

    public Optional<ProductCategory> getProductCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        Location productLocation = locationService.createLocation (productCategory.getLocation());
        productCategory.setLocation(productLocation);

        return productCategoryRepository.save(productCategory);
    }


    public Optional<ProductCategory> updateProductCategory(ProductCategory productCategory) {
        Long productCategoryId = productCategory.getId();
        if (productCategoryId == null) {
            throw new IllegalArgumentException("ProductCategory id must not be null");
        }

        productCategoryRepository.findById(productCategoryId)
                                 .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productCategoryId));

        Location productLocation = locationService.createLocation(productCategory.getLocation());
        productCategory.setLocation(productLocation);


        return Optional.of(productCategoryRepository.save(productCategory));
    }

    public void deleteProductCategory(Long id) {productCategoryRepository.findById(id);}

    public ProductCategory createProductCategoryV2(ProductCategoryDTO createProductCategoryDTO){
        Location location = locationService.getLocationById(createProductCategoryDTO.getLocationId())
                                           .orElseThrow(() -> new IllegalArgumentException("Invalid location id" + createProductCategoryDTO.getLocationId()));
        ProductCategory productCategory = mapCreateProductCategoryDTOToProductCategory(createProductCategoryDTO, location);
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategoryV2(Long id, ProductCategoryDTO updateProductCategoryDTO){
        ProductCategory retrievedProductCategory = productCategoryRepository.findById(id)
                                                                            .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product category with id " + id));
        log.info("Retrieved product category {}", retrievedProductCategory);

        Long productLocationId = updateProductCategoryDTO.getLocationId();
        Location location = locationService.getLocationById(productLocationId)
                                           .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + productLocationId));

        mapUpdateProductCategoryDTOToProductCategory(updateProductCategoryDTO, location, retrievedProductCategory);
        log.info("Retrieved product  category after mapping {}", retrievedProductCategory);
        return productCategoryRepository.save(retrievedProductCategory);
    }
}

