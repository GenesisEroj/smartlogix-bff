package com.smartlogix.bff;

import com.smartlogix.bff.dto.EstadoDTO;
import com.smartlogix.bff.dto.PedidoDTO;
import com.smartlogix.bff.service.PedidosService;
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
class PedidosServiceTest {

    @Mock private WebClient webClient;
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock private WebClient.ResponseSpec responseSpec;
    @Mock private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock private WebClient.RequestBodySpec requestBodySpec;

    private PedidosService pedidosService;

    private final PedidoDTO mockPedido = new PedidoDTO(
            1L, "cliente-001", "PROD-001", 2, "PENDIENTE", null, 0.0
    );

    @BeforeEach
    void setUp() {
        pedidosService = new PedidosService(webClient);
    }

    @Test
    void getPedidoById_debeRetornarPedido() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PedidoDTO.class)).thenReturn(Mono.just(mockPedido));

        PedidoDTO result = pedidosService.getPedidoById(1L);

        assertNotNull(result);
        assertEquals("cliente-001", result.getClienteId());
        assertEquals("PENDIENTE", result.getEstado());
    }

    @Test
    void createPedido_debeRetornarPedidoCreado() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PedidoDTO.class)).thenReturn(Mono.just(mockPedido));

        PedidoDTO result = pedidosService.createPedido(mockPedido);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2, result.getCantidad());
    }

    @Test
    void updateEstado_debeCambiarEstadoCorrectamente() {
        PedidoDTO enviado = new PedidoDTO(
                1L, "cliente-001", "PROD-001", 2, "ENVIADO", null, 0.0
        );

        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PedidoDTO.class)).thenReturn(Mono.just(enviado));

        PedidoDTO result = pedidosService.updateEstado(1L, new EstadoDTO("ENVIADO"));

        assertNotNull(result);
        assertEquals("ENVIADO", result.getEstado());
    }

    @Test
    void deletePedido_debeCompletarSinError() {
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> pedidosService.deletePedido(1L));
    }
}
