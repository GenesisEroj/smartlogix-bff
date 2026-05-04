package com.smartlogix.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private String clienteId;
    private String productoId;
    private Integer cantidad;
    private String estado;
    private String fechaCreacion;
}