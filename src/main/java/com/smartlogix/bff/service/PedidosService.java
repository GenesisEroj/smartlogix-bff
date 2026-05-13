package com.smartlogix.bff.service;

import com.smartlogix.bff.dto.EstadoDTO;
import com.smartlogix.bff.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * PedidosService
 * PATRÓN: Strategy
 * Encapsula toda la lógica de comunicación con el ms-pedidos.
 */
@Service
public class PedidosService {

    private final WebClient client;

    public PedidosService(@Qualifier("pedidosClient") WebClient client) {
        this.client = client;
    }

    public List<PedidoDTO> getPedidos() {
        return client.get()
                .uri("/api/pedidos")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PedidoDTO>>() {
                })
                .block();
    }

    public PedidoDTO getPedidoById(Long id) {
        return client.get()
                .uri("/api/pedidos/{id}", id)
                .retrieve()
                .bodyToMono(PedidoDTO.class)
                .block();
    }

    public PedidoDTO createPedido(PedidoDTO dto) {
        return client.post()
                .uri("/api/pedidos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(PedidoDTO.class)
                .block();
    }

    public PedidoDTO updateEstado(Long id, EstadoDTO estadoDTO) {
        return client.put()
                .uri("/api/pedidos/{id}/estado?estado={estado}", id, estadoDTO.getEstado())
                .retrieve()
                .bodyToMono(PedidoDTO.class)
                .block();
    }

    public Mono<Void> deletePedido(Long id) {
        return client.delete()
                .uri("/api/pedidos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
