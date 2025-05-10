package com.repofetcher.usdtoplninvoiceapp.service;

import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ComputerService {
    private final ComputerRepository repository;
    private final NbpApiService nbpApiService;

    public ComputerService(ComputerRepository repository, NbpApiService nbpApiService) {
        this.repository = repository;
        this.nbpApiService = nbpApiService;
    }

    public void addComputer(String name, BigDecimal costUSD, LocalDate date) {
        BigDecimal rate = nbpApiService.getExchangeRate(date.toString());
        BigDecimal costPLN = costUSD.multiply(rate);
        Computer c = new Computer();
        c.setName(name);
        c.setAccountingDate(date);
        c.setCostUSD(costUSD);
        c.setCostPLN(costPLN);
        repository.save(c);
    }
}