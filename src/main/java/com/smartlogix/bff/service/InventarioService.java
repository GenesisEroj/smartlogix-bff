package com.smartlogix.bff.service;

import com.smartlogix.bff.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * InventarioService
 * PATRÓN: Strategy
 * Encapsula toda la lógica de comunicación con el ms-inventario.
 * El controlador no necesita saber cómo ni a quién llama.
 */
@Service
public class InventarioService {

    private final WebClient client;

    public InventarioService(@Qualifier("inventarioClient") WebClient client) {
        this.client = client;
    }

    public List<ProductoDTO> getProductos() {
        return client.get()
                .uri("/api/inventario")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductoDTO>>() {})
                .block();
    }

    public ProductoDTO getProductoById(Long id) {
        return client.get()
                .uri("/api/inventario/{id}", id)
                .retrieve()
                .bodyToMono(ProductoDTO.class)
                .block();
    }

    public ProductoDTO createProducto(ProductoDTO dto) {
        return client.post()
                .uri("/api/inventario")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProductoDTO.class)
                .block();
    }

    public ProductoDTO updateProducto(Long id, ProductoDTO dto) {
        return client.put()
                .uri("/api/inventario/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProductoDTO.class)
                .block();
    }

    public Mono<Void> deleteProducto(Long id) {
        return client.delete()
                .uri("/api/inventario/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
