package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.Sales;
import com.springboot.stockmanagementhub.model.dto.SaleResponseDTO;
import com.springboot.stockmanagementhub.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<List<Sales>> getAllSale() {
        return ResponseEntity.ok().body(salesService.getAllSale());
    }

    @GetMapping("/v2/sales")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<List<Sales>> getAllSale2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize){

        Page<Sales> sales = salesService.getAllSale2(pageNo, pageSize);
        return ResponseEntity.ok(sales.getContent());
    }
    @GetMapping("/v3/sales")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<List<Sales>> getAllSales3(Pageable pageable){
        Page<Sales> sales = salesService.getAllSales3(pageable);
        return ResponseEntity.ok(sales.getContent());
    }

    @GetMapping("/sales/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<?> getSaleByID(@PathVariable Long id) {
        log.info("Get sale id by " + id);
        if (id < 1) {
            throw new IllegalArgumentException("Sales ID cannot be less than 1, please input correct ID");
        }
        return salesService.getSaleById(id)
                           .map(sale -> ResponseEntity.ok().body(sale))
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/sales-by-date")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES ATTENDANT')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByDate(@PathVariable LocalDateTime salesDate) {
        List<SaleResponseDTO> salesResponseDTOs = salesService.getSalesByDate(salesDate);
        return ResponseEntity.ok(salesResponseDTOs);

    }

    @DeleteMapping("/sales/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Sales> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}


