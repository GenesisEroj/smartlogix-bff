package com.smartlogix.bff.client;

import com.smartlogix.bff.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MsPedidosClient {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8081/api/pedidos";

    public List<PedidoDTO> obtenerTodos() {
        PedidoDTO[] pedidos = restTemplate.getForObject(BASE_URL, PedidoDTO[].class);
        return Arrays.asList(pedidos != null ? pedidos : new PedidoDTO[0]);
    }

    public PedidoDTO obtenerPorId(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, PedidoDTO.class);
    }

    public PedidoDTO crearPedido(PedidoDTO pedido) {
        return restTemplate.postForObject(BASE_URL, pedido, PedidoDTO.class);
    }
}