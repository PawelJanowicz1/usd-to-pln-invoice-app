package com.repofetcher.usdtoplninvoiceapp.service;

import com.repofetcher.usdtoplninvoiceapp.dto.ComputerDto;
import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    public Page<ComputerDto> searchAndSort(String name, LocalDate date, Pageable pageable) {
        Page<Computer> page;

        if (name != null && date != null) {
            page = repository.findByNameContainingIgnoreCaseAndAccountingDate(name, date, pageable);
        } else if (name != null) {
            page = repository.findByNameContainingIgnoreCase(name, pageable);
        } else if (date != null) {
            page = repository.findByAccountingDate(date, pageable);
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(c -> new ComputerDto(
                c.getName(),
                c.getAccountingDate(),
                c.getCostUSD(),
                c.getCostPLN()
        ));
    }
}