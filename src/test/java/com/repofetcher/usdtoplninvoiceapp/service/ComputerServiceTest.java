package com.repofetcher.usdtoplninvoiceapp.service;

import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComputerServiceTest {

    @Mock
    private ComputerRepository repository;

    @Mock
    private NbpApiService nbpApiService;

    @InjectMocks
    private ComputerService computerService;

    @Test
    void addComputer_shouldSaveComputerWithConvertedPLN() {
        String name = "Laptop Razer";
        BigDecimal costUSD = new BigDecimal("403");
        LocalDate date = LocalDate.of(2024, 10, 1);
        BigDecimal rate = new BigDecimal("4.5");

        when(nbpApiService.getExchangeRate(anyString())).thenReturn(rate);
        computerService.addComputer(name, costUSD, date);

        verify(repository).save(argThat(computer ->
                computer.getCostPLN().equals(costUSD.multiply(rate)) &&
                        computer.getName().equals(name) &&
                        computer.getAccountingDate().equals(date)
        ));
    }
}