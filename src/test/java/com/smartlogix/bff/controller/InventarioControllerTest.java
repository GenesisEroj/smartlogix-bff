package com.smartlogix.bff.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogix.bff.dto.ProductoDTO;
import com.smartlogix.bff.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
@DisplayName("BFF - InventarioController Test")
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoDTO productoMock;

    @BeforeEach
    void setUp() {
        productoMock = new ProductoDTO(
                1L, "PROD-001", "Laptop HP", 899.99, 50, "Electrónica", null
        );
    }

    @Test
    @DisplayName("GET /api/inventario - debe retornar todos los productos")
    void getProductos_debeRetornar200() throws Exception {
        ProductoDTO producto2 = new ProductoDTO(
                2L, "PROD-002", "Mouse", 19.99, 100, "Periféricos", null
        );
        when(inventarioService.getProductos()).thenReturn(Arrays.asList(productoMock, producto2));

        mockMvc.perform(get("/api/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productoId").value("PROD-001"))
                .andExpect(jsonPath("$[1].productoId").value("PROD-002"));

        verify(inventarioService, times(1)).getProductos();
    }

    @Test
    @DisplayName("GET /api/inventario/{id} - debe retornar producto por ID")
    void getProductoById_debeRetornar200() throws Exception {
        when(inventarioService.getProductoById(1L)).thenReturn(productoMock);

        mockMvc.perform(get("/api/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value("PROD-001"))
                .andExpect(jsonPath("$.nombre").value("Laptop HP"));

        verify(inventarioService, times(1)).getProductoById(1L);
    }

    @Test
    @DisplayName("POST /api/inventario - debe crear producto")
    void createProducto_debeRetornar201() throws Exception {
        when(inventarioService.createProducto(any(ProductoDTO.class))).thenReturn(productoMock);

        mockMvc.perform(post("/api/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productoId").value("PROD-001"));

        verify(inventarioService, times(1)).createProducto(any(ProductoDTO.class));
    }

    @Test
    @DisplayName("PUT /api/inventario/{id} - debe actualizar producto")
    void updateProducto_debeRetornar200() throws Exception {
        when(inventarioService.updateProducto(eq(1L), any(ProductoDTO.class))).thenReturn(productoMock);

        mockMvc.perform(put("/api/inventario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoMock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value("PROD-001"));

        verify(inventarioService, times(1)).updateProducto(eq(1L), any(ProductoDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/inventario/{id} - debe eliminar producto")
    void deleteProducto_debeRetornar204() throws Exception {
        when(inventarioService.deleteProducto(1L)).thenReturn(Mono.empty());

        mockMvc.perform(delete("/api/inventario/1"))
                .andExpect(status().isNoContent());

        verify(inventarioService, times(1)).deleteProducto(1L);
    }
}
