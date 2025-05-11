package com.repofetcher.usdtoplninvoiceapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ComputerDto(String name,
                          LocalDate accountingDate,
                          BigDecimal costUSD,
                          BigDecimal costPLN) {
}
