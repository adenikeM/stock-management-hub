package com.springboot.stockmanagementhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Product name cannot be null")
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private Integer availableQuantity;

    @Column(length = 20, nullable = false)
    private BigDecimal price;

    @Column(length = 20, nullable = false)
    private String colour;

    @Past
    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    @Column(length = 20, nullable = false)
    private String size;

    @ManyToOne
    private ProductCategory productCategory;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "settings_key")
    @Column(name = "settings_value")
    @CollectionTable(name = "product_settings", joinColumns = @JoinColumn(name = "product_id"))
    private final Map<String, String> settings = new HashMap<>();

    //method for adding custom property
    public void addSetting(String key, String value) {
        this.settings.put(key, value);
    }
}
