package com.repofetcher.usdtoplninvoiceapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class NbpRestClientConfig {
    @Value("${nbp.exchangers}")
    private String exchangers;
    @Primary
    @Bean
    public RestClient nbpRestClient() {
        return RestClient.builder()
                .baseUrl(exchangers)
                .build();
    }
}