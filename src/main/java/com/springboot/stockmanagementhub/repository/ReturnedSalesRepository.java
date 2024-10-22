package com.springboot.stockmanagementhub.repository;

import com.springboot.stockmanagementhub.model.ReturnedSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnedSalesRepository extends JpaRepository<ReturnedSales, Long> {
    boolean existsByProduct_Id(Long id);
}
