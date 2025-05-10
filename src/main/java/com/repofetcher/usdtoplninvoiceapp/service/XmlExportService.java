package com.repofetcher.usdtoplninvoiceapp.service;

import com.repofetcher.usdtoplninvoiceapp.dto.Invoice;
import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

@Service
public class XmlExportService {
    public void exportToXml(List<Computer> computers) throws Exception {
        Invoice invoice = new Invoice();
        invoice.setComputers(computers);
        JAXBContext context = JAXBContext.newInstance(Invoice.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(invoice, new File("invoice.xml"));
    }
}
