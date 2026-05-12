package com.smartlogix.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig
 * PATRÓN: Factory Method
 * Crea instancias de WebClient configuradas para cada microservicio.
 * El BFF nunca accede directamente a la BD; todo pasa por estos clientes.
 */
@Configuration
public class WebClientConfig {

    @Value("${ms.inventario.url}")
    private String inventarioUrl;

    @Value("${ms.pedidos.url}")
    private String pedidosUrl;

    @Bean(name = "inventarioClient")
    public WebClient inventarioClient() {
        return WebClient.builder()
                .baseUrl(inventarioUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean(name = "pedidosClient")
    public WebClient pedidosClient() {
        return WebClient.builder()
                .baseUrl(pedidosUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
