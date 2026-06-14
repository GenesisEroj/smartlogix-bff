package com.smartlogix.bff.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogix.bff.dto.EstadoDTO;
import com.smartlogix.bff.dto.PedidoDTO;
import com.smartlogix.bff.service.PedidosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidosController.class)
@DisplayName("BFF - PedidosController Test")
class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidosService pedidosService;

    @Autowired
    private ObjectMapper objectMapper;

    private PedidoDTO pedidoMock;

    @BeforeEach
    void setUp() {
        pedidoMock = new PedidoDTO(
                1L, "cliente-001", "PROD-001", 2, "PENDIENTE", null, 89.98
        );
    }

    @Test
    @DisplayName("GET /api/pedidos - debe retornar todos los pedidos")
    void getPedidos_debeRetornar200() throws Exception {
        PedidoDTO pedido2 = new PedidoDTO(
                2L, "cliente-002", "PROD-002", 5, "ENVIADO", null, 99.95
        );
        when(pedidosService.getPedidos()).thenReturn(Arrays.asList(pedidoMock, pedido2));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].clienteId").value("cliente-001"))
                .andExpect(jsonPath("$[1].clienteId").value("cliente-002"));

        verify(pedidosService, times(1)).getPedidos();
    }

    @Test
    @DisplayName("GET /api/pedidos/{id} - debe retornar pedido por ID")
    void getPedidoById_debeRetornar200() throws Exception {
        when(pedidosService.getPedidoById(1L)).thenReturn(pedidoMock);

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("cliente-001"))
                .andExpect(jsonPath("$.productoId").value("PROD-001"));

        verify(pedidosService, times(1)).getPedidoById(1L);
    }

    @Test
    @DisplayName("POST /api/pedidos - debe crear pedido")
    void createPedido_debeRetornar201() throws Exception {
        when(pedidosService.createPedido(any(PedidoDTO.class))).thenReturn(pedidoMock);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value("cliente-001"));

        verify(pedidosService, times(1)).createPedido(any(PedidoDTO.class));
    }

    @Test
    @DisplayName("PUT /api/pedidos/{id}/estado - debe actualizar estado de pedido")
    void updateEstado_debeRetornar200() throws Exception {
        EstadoDTO estadoDTO = new EstadoDTO("ENVIADO");
        PedidoDTO actualizado = new PedidoDTO(
                1L, "cliente-001", "PROD-001", 2, "ENVIADO", null, 89.98
        );
        when(pedidosService.updateEstado(eq(1L), any(EstadoDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/pedidos/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ENVIADO"));

        verify(pedidosService, times(1)).updateEstado(eq(1L), any(EstadoDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/pedidos/{id} - debe eliminar pedido")
    void deletePedido_debeRetornar204() throws Exception {
        when(pedidosService.deletePedido(1L)).thenReturn(Mono.empty());

        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidosService, times(1)).deletePedido(1L);
    }
}
