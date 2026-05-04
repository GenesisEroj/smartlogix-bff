package com.smartlogix.bff.controller;

import com.smartlogix.bff.dto.PedidoDTO;
import com.smartlogix.bff.dto.ProductoDTO;
import com.smartlogix.bff.service.BffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bff")
@RequiredArgsConstructor
public class BffController {

    private final BffService bffService;

    // Pedidos
    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidos() {
        return ResponseEntity.ok(bffService.obtenerPedidos());
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bffService.obtenerPedidoPorId(id));
    }

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bffService.crearPedido(pedido));
    }

    // Inventario
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> obtenerProductos() {
        return ResponseEntity.ok(bffService.obtenerProductos());
    }

    @GetMapping("/productos/{productoId}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable String productoId) {
        return ResponseEntity.ok(bffService.obtenerProductoPorId(productoId));
    }
}