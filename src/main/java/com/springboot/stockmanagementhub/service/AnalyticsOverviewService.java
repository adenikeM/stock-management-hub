package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.AnalyticsOverview;
import com.springboot.stockmanagementhub.repository.ProductCategoryRepository;
import com.springboot.stockmanagementhub.repository.ProductRepository;
import com.springboot.stockmanagementhub.repository.ReturnedSalesRepository;
import com.springboot.stockmanagementhub.repository.SalesRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsOverviewService {
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final ReturnedSalesRepository returnedSalesRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public AnalyticsOverviewService(ProductRepository productRepository, SalesRepository salesRepository, ReturnedSalesRepository returnedSalesRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.salesRepository = salesRepository;
        this.returnedSalesRepository = returnedSalesRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public AnalyticsOverview getAnalyticsOverview() {
        return AnalyticsOverview.builder()
                                .productCount(productRepository.count())
                                .salesCount(salesRepository.count())
                                .returnedSalesCount(returnedSalesRepository.count())
                                .productCategoryCount(productCategoryRepository.count())
                                .build();

//        List<ProductCategory> categories = productCategoryRepository.findAll();
//        List<ProductCategoryCount> categoryCounts = new ArrayList<>();
//        for(ProductCategory category : categories) {
//            Long count = productRepository.countByProductCategory(category);
//            ProductCategoryC;ount categoryCount = new ProductCategoryCount(category.getCategoryName(), count);
//            categoryCounts.add(categoryCount);
//        }
//        overview.setCategoryCounts(categoryCounts);
    }
}


