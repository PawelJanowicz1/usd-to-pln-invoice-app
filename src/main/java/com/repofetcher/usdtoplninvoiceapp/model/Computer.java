package com.repofetcher.usdtoplninvoiceapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotNull(message = "Accounting date is required")
    private LocalDate accountingDate;

    @NotNull(message = "USD cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "USD cost must be positive")
    private BigDecimal costUSD;

    @NotNull(message = "PLN cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "PLN cost must be positive")
    private BigDecimal costPLN;
}