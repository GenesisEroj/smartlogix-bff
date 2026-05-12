package com.smartlogix.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * ProductoDTO
 * Objeto de transferencia para el módulo de inventario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String productoId;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String categoria;
    private LocalDateTime fechaActualizacion;
}
