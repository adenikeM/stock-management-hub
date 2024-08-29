package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.Product;
import com.springboot.stockmanagementhub.model.Sales;
import com.springboot.stockmanagementhub.model.dto.SaleResponseDTO;
import com.springboot.stockmanagementhub.repository.ReturnedSalesRepository;
import com.springboot.stockmanagementhub.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final ReturnedSalesRepository returnedSalesRepository;

    public List<Sales> getAllSale() {
        return salesRepository.findAll();
    }

    public Page<Sales> getAllSale2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return salesRepository.findAll(pageable);
    }
    public Page<Sales> getAllSales3(Pageable pageable){
        return salesRepository.findAll(pageable);
    }
    public Optional<Sales> getSaleById(Long id) {
        return salesRepository.findById(id);
    }


    public List<SaleResponseDTO> getSalesByDate(LocalDateTime saleDate) {
        List<Sales> sales = salesRepository.findBySalesDate(saleDate);
        List<SaleResponseDTO> responses = new ArrayList<>();
        for (Sales sale : sales) {
            SaleResponseDTO response = new SaleResponseDTO();
            response.setProductName(sale.getProducts().stream().map(Product::getName).collect(Collectors.joining(", ")));
            response.setQuantitySold(sale.getTotalQuantitySold());
            response.setSaleDate(sale.getSalesDate());
            response.setTotalPrice(sale.getPrice());
            responses.add(response);
        }
        return responses;
    }

    public void deleteSale(Long id) {
        salesRepository.findById(id);
    }

    public Sales createSale(Sales sales) {
        log.info("request to create new sales {} ", sales);
        if (!Objects.isNull(sales.getId())) {
            throw new IllegalArgumentException("New sales cannot have id");
        }
        return salesRepository.save(sales);
    }

}
