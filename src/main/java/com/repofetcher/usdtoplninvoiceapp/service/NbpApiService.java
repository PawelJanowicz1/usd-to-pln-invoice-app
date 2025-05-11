package com.repofetcher.usdtoplninvoiceapp.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class NbpApiService {

    private final RestClient nbpRestClient;

    public NbpApiService(@Qualifier("nbpRestClient") RestClient nbpRestClient) {
        this.nbpRestClient = nbpRestClient;
    }

    public BigDecimal getExchangeRate(String date) {
        Map response = nbpRestClient.get()
                .uri("/{date}?format=json", date)
                .retrieve()
                .body(Map.class);

        var rate = ((Map)((java.util.List) response.get("rates")).get(0)).get("mid");
        return new BigDecimal(rate.toString());
    }
}