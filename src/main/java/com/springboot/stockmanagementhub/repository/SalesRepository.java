package com.springboot.stockmanagementhub.repository;

import com.springboot.stockmanagementhub.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findBySalesDate(LocalDateTime date);
    boolean existsByProducts_Id(Long id);
}
