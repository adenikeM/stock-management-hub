package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.MailDetails;
import com.springboot.stockmanagementhub.model.Product;
import com.springboot.stockmanagementhub.model.ReturnedSales;
import com.springboot.stockmanagementhub.model.Sales;
import com.springboot.stockmanagementhub.model.dto.ProductReturn;
import com.springboot.stockmanagementhub.model.enumeration.ReturnReason;
import com.springboot.stockmanagementhub.repository.ProductRepository;
import com.springboot.stockmanagementhub.repository.ReturnedSalesRepository;
import com.springboot.stockmanagementhub.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnedSalesService {
    private final ReturnedSalesRepository returnedSalesRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    public ReturnedSales createReturnedSales(Long salesId, List<ProductReturn> productReturn, ReturnReason reasonForReturn, String additionalComments) {
        // Retrieve sales entity
        Sales sales = salesRepository.findById(salesId).orElseThrow(() -> new RuntimeException("Sales with id " + salesId + " not found"));

        BigDecimal totalRefundAmount = BigDecimal.ZERO;
        int totalQuantityReturned = 0;

        ReturnedSales returnedSales = new ReturnedSales();

        for (ProductReturn productReturn1 : productReturn) {
            Long productId = productReturn1.getProductId();
            Integer quantityReturned = productReturn1.getQuantityReturned();

            totalQuantityReturned += quantityReturned;

            // Retrieve product entity
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));

            // Check if the product is part of the sales
            if (!sales.getProducts().contains(product)) {
                throw new RuntimeException("Product with id " + productId + " is not associated with sales id " + salesId);
            }

            // Check if quantity returned is valid
            if (quantityReturned <= 0 || quantityReturned > sales.getTotalQuantitySold()) {
                throw new IllegalArgumentException("Invalid quantity returned");
            }

            // Calculate refund amount
            BigDecimal refundAmount = calculateRefundAmount(product.getPrice(), quantityReturned);
            totalRefundAmount = totalRefundAmount.add(refundAmount);

            //check if the returned product can be added back to stock
            if(isProductReturnAllowedToUpdateQuantity(reasonForReturn)) {
                // Update product available quantity
                int newAvailableQuantity = product.getAvailableQuantity() + quantityReturned;
                product.setAvailableQuantity(newAvailableQuantity);
                productRepository.save(product);
            }

            // set properties of returned sales entity
            returnedSales.setSales(sales);
            returnedSales.setProduct(product);
            returnedSales.setQuantityReturned(totalQuantityReturned);
            returnedSales.setReasonForReturn(reasonForReturn);
            returnedSales.setAdditionalComments(additionalComments);
            returnedSales.setReturnDate(LocalDate.now());

            // save returnedSales entity
            returnedSales = returnedSalesRepository.save(returnedSales);
        }

        // send mail to customer
        emailService.sendRefundEmail(MailDetails.builder()
                                                .recipient(sales.getCustomerInfo().getEmail())
                                                .subject("Product Return and Refund Confirmation")
                                                .msgBody("Dear Customer, \n\n"
                                                        + "We have received your returned products. \n"
                                                        + "The refund amount of " + totalRefundAmount + " has been processed and confirm"
                                                        + "Thank you for shopping with us. \n\n"
                                                        + "Best regards")
                                                .build());
        return returnedSales;
    }

    public List<ReturnedSales> getReturnedSales() {
        return returnedSalesRepository.findAll();
    }

    public ReturnedSales getReturnedSalesById(Long id) {
        return returnedSalesRepository.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("Return sales id not found"));
    }

    private BigDecimal calculateRefundAmount(BigDecimal price, int quantityReturned) {
        return price.multiply(BigDecimal.valueOf(quantityReturned));
    }
    private boolean isProductReturnAllowedToUpdateQuantity(ReturnReason reasonForReturn){

        return reasonForReturn != ReturnReason.DAMAGED && reasonForReturn != ReturnReason.EXPIRED;
    }

}
