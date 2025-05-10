package com.repofetcher.usdtoplninvoiceapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Primary
    @Bean
    public RestClient nbpRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.nbp.pl/api/exchangerates/rates/A/USD")
                .build();
    }
}