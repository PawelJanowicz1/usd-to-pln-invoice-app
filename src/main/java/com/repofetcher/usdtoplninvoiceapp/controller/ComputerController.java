package com.repofetcher.usdtoplninvoiceapp.controller;

import com.repofetcher.usdtoplninvoiceapp.dto.ComputerDto;
import com.repofetcher.usdtoplninvoiceapp.dto.ComputerRequest;
import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import com.repofetcher.usdtoplninvoiceapp.service.ComputerService;
import com.repofetcher.usdtoplninvoiceapp.service.XmlExportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/computers")
public class ComputerController {
    private final ComputerRepository repository;
    private final ComputerService computerService;
    private final XmlExportService xmlExportService;

    public ComputerController(ComputerService computerService, XmlExportService xmlExportService, ComputerRepository repository) {
        this.computerService = computerService;
        this.xmlExportService = xmlExportService;
        this.repository = repository;
    }

    @PostMapping("/add")
    public String addComputer(@RequestBody ComputerRequest request) throws Exception {
        computerService.addComputer(request.name(), request.costUSD(), request.accountingDate());
        xmlExportService.exportToXml(repository.findAll());
        return "Computer added and exported to invoice.xml";
    }

    @GetMapping
    public Page<ComputerDto> findComputers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return computerService.searchAndSort(name, date, pageable);
    }
}