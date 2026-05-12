package com.smartlogix.bff;

import com.smartlogix.bff.dto.ProductoDTO;
import com.smartlogix.bff.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock private WebClient.ResponseSpec responseSpec;
    @Mock private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock private WebClient.RequestBodySpec requestBodySpec;

    private InventarioService inventarioService;

    private final ProductoDTO mockProducto = new ProductoDTO(
            1L, "PROD-001", "Camiseta", 15000.0, 100, "Ropa", null
    );

    @BeforeEach
    void setUp() {
        inventarioService = new InventarioService(webClient);
    }

    @Test
    void getProductoById_debeRetornarProducto() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO.class)).thenReturn(Mono.just(mockProducto));

        ProductoDTO result = inventarioService.getProductoById(1L);

        assertNotNull(result);
        assertEquals("Camiseta", result.getNombre());
        assertEquals(100, result.getStock());
    }

    @Test
    void createProducto_debeRetornarProductoCreado() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO.class)).thenReturn(Mono.just(mockProducto));

        ProductoDTO result = inventarioService.createProducto(mockProducto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(15000.0, result.getPrecio());
    }

    @Test
    void deleteProducto_debeCompletarSinError() {
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> inventarioService.deleteProducto(1L));
    }
}
