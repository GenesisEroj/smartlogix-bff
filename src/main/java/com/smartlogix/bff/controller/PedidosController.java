package com.smartlogix.bff.controller;

import com.smartlogix.bff.dto.EstadoDTO;
import com.smartlogix.bff.dto.PedidoDTO;
import com.smartlogix.bff.service.PedidosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidosService pedidosService;

    public PedidosController(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos() {
        return ResponseEntity.ok(pedidosService.getPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidosService.getPedidoById(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidosService.createPedido(dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> updateEstado(@PathVariable Long id,
                                                   @RequestBody EstadoDTO estadoDTO) {
        return ResponseEntity.ok(pedidosService.updateEstado(id, estadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidosService.deletePedido(id).block();
        return ResponseEntity.noContent().build();
    }
}