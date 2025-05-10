package com.repofetcher.usdtoplninvoiceapp.controller;

import com.repofetcher.usdtoplninvoiceapp.dto.ComputerRequest;
import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import com.repofetcher.usdtoplninvoiceapp.repository.ComputerRepository;
import com.repofetcher.usdtoplninvoiceapp.service.ComputerService;
import com.repofetcher.usdtoplninvoiceapp.service.XmlExportService;
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

    @GetMapping("/search")
    public List<Computer> search(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String date) {
        if (name != null) return repository.findByNameContainingIgnoreCase(name);
        if (date != null) return repository.findByAccountingDate(LocalDate.parse(date));
        return repository.findAll();
    }

    @GetMapping("/sort")
    public List<Computer> sort(@RequestParam String by, @RequestParam String order) {
        if (by.equals("name")) {
            return order.equals("asc") ? repository.sortByNameAsc() : repository.sortByNameDesc();
        }
        if (by.equals("date")) {
            return order.equals("asc") ? repository.sortByAccountingDateAsc() : repository.sortByAccountingDateDesc();
        }
        return repository.findAll();
    }
}