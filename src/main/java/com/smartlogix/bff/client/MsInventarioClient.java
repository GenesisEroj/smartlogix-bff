package com.smartlogix.bff.client;

import com.smartlogix.bff.dto.ProductoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MsInventarioClient {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8082/api/inventario";

    public List<ProductoDTO> obtenerTodos() {
        ProductoDTO[] productos = restTemplate.getForObject(BASE_URL, ProductoDTO[].class);
        return Arrays.asList(productos != null ? productos : new ProductoDTO[0]);
    }

    public ProductoDTO obtenerPorProductoId(String productoId) {
        return restTemplate.getForObject(BASE_URL + "/" + productoId, ProductoDTO.class);
    }

    public ProductoDTO actualizarStock(String productoId, Integer cantidad) {
        return restTemplate.patchForObject(
                BASE_URL + "/" + productoId + "/stock?cantidad=" + cantidad,
                null,
                ProductoDTO.class
        );
    }
}