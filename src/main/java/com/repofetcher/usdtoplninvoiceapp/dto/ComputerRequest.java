package com.repofetcher.usdtoplninvoiceapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ComputerRequest(String name,
                              BigDecimal costUSD,
                              LocalDate accountingDate) {
}
