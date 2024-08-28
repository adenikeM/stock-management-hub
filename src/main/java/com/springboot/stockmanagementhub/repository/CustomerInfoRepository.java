package com.springboot.stockmanagementhub.repository;

import com.springboot.stockmanagementhub.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
}
