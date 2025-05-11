package com.repofetcher.usdtoplninvoiceapp.dto;

import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@XmlRootElement(name = "invoice")
@Getter
@Setter
public class Invoice {
    private List<Computer> computers;
}