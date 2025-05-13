package com.repofetcher.usdtoplninvoiceapp.service;

import com.repofetcher.usdtoplninvoiceapp.dto.ComputerDto;
import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        BigDecimal costUSD = new BigDecimal("400");
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

    @Test
    void searchAndSort_shouldReturnComputers_whenNameAndDateProvided() {
        String name = "Laptop";
        LocalDate date = LocalDate.of(2024, 10, 1);
        Pageable pageable = Pageable.unpaged();
        Computer computer = new Computer();
        computer.setName("Laptop Razer");
        computer.setAccountingDate(date);
        computer.setCostUSD(new BigDecimal("400"));
        computer.setCostPLN(new BigDecimal("1800"));

        Page<Computer> page = new PageImpl<>(List.of(computer));

        when(repository.findByNameContainingIgnoreCaseAndAccountingDate(name, date, pageable)).thenReturn(page);

        Page<ComputerDto> result = computerService.searchAndSort(name, date, pageable);

        verify(repository).findByNameContainingIgnoreCaseAndAccountingDate(name, date, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop Razer", result.getContent().get(0).name());
        assertEquals(date, result.getContent().get(0).accountingDate());
    }

    @Test
    void searchAndSort_shouldReturnComputers_whenOnlyNameProvided() {
        String name = "Laptop";
        LocalDate date = null;
        Pageable pageable = Pageable.unpaged();
        Computer computer = new Computer();
        computer.setName("Laptop Razer");
        computer.setAccountingDate(LocalDate.of(2024, 10, 1));
        computer.setCostUSD(new BigDecimal("400"));
        computer.setCostPLN(new BigDecimal("1800"));

        Page<Computer> page = new PageImpl<>(List.of(computer));

        when(repository.findByNameContainingIgnoreCase(name, pageable)).thenReturn(page);

        Page<ComputerDto> result = computerService.searchAndSort(name, date, pageable);

        verify(repository).findByNameContainingIgnoreCase(name, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop Razer", result.getContent().get(0).name());
        assertEquals(LocalDate.of(2024, 10, 1), result.getContent().get(0).accountingDate());
    }

    @Test
    void searchAndSort_shouldReturnComputers_whenOnlyDateProvided() {
        String name = null;
        LocalDate date = LocalDate.of(2024, 10, 1);
        Pageable pageable = Pageable.unpaged();
        Computer computer = new Computer();
        computer.setName("Laptop Razer");
        computer.setAccountingDate(date);
        computer.setCostUSD(new BigDecimal("400"));
        computer.setCostPLN(new BigDecimal("1800"));

        Page<Computer> page = new PageImpl<>(List.of(computer));

        when(repository.findByAccountingDate(date, pageable)).thenReturn(page);

        Page<ComputerDto> result = computerService.searchAndSort(name, date, pageable);

        verify(repository).findByAccountingDate(date, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(date, result.getContent().get(0).accountingDate());
    }

    @Test
    void searchAndSort_shouldReturnComputers_whenNoNameOrDateProvided() {
        String name = null;
        LocalDate date = null;
        Pageable pageable = Pageable.unpaged();
        Computer computer = new Computer();
        computer.setName("Laptop Razer");
        computer.setAccountingDate(LocalDate.of(2024, 10, 1));
        computer.setCostUSD(new BigDecimal("400"));
        computer.setCostPLN(new BigDecimal("1800"));

        Page<Computer> page = new PageImpl<>(List.of(computer));

        when(repository.findAll(pageable)).thenReturn(page);

        Page<ComputerDto> result = computerService.searchAndSort(name, date, pageable);

        verify(repository).findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop Razer", result.getContent().get(0).name());
        assertEquals(LocalDate.of(2024, 10, 1), result.getContent().get(0).accountingDate());
    }

    @Test
    void searchAndSort_shouldReturnEmpty_whenNoResults() {
        String name = "NonExistentLaptop";
        LocalDate date = LocalDate.of(2024, 10, 1);
        Pageable pageable = Pageable.unpaged();
        Page<Computer> emptyPage = Page.empty();

        when(repository.findByNameContainingIgnoreCaseAndAccountingDate(name, date, pageable)).thenReturn(emptyPage);

        Page<ComputerDto> result = computerService.searchAndSort(name, date, pageable);

        verify(repository).findByNameContainingIgnoreCaseAndAccountingDate(name, date, pageable);

        assertTrue(result.getContent().isEmpty());
    }
}