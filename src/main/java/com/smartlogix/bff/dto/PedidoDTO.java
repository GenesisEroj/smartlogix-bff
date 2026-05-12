package com.smartlogix.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * PedidoDTO
 * Objeto de transferencia para el módulo de pedidos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private String clienteId;
    private String productoId;
    private Integer cantidad;
    private String estado;
    private LocalDateTime fechaCreacion;
}
