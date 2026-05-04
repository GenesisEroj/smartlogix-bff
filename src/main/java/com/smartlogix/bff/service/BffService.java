package com.smartlogix.bff.service;

import com.smartlogix.bff.client.MsInventarioClient;
import com.smartlogix.bff.client.MsPedidosClient;
import com.smartlogix.bff.dto.PedidoDTO;
import com.smartlogix.bff.dto.ProductoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BffService {

    private final MsPedidosClient msPedidosClient;
    private final MsInventarioClient msInventarioClient;

    // Obtener todos los pedidos
    public List<PedidoDTO> obtenerPedidos() {
        return msPedidosClient.obtenerTodos();
    }

    // Obtener pedido por id
    public PedidoDTO obtenerPedidoPorId(Long id) {
        return msPedidosClient.obtenerPorId(id);
    }

    // Crear pedido y actualizar stock
    public PedidoDTO crearPedido(PedidoDTO pedido) {
        // Verificar stock disponible antes de crear el pedido
        ProductoDTO producto = msInventarioClient.obtenerPorProductoId(pedido.getProductoId());
        if (producto.getStock() < pedido.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + pedido.getProductoId());
        }
        // Crear el pedido
        PedidoDTO nuevoPedido = msPedidosClient.crearPedido(pedido);
        // Actualizar stock
        msInventarioClient.actualizarStock(pedido.getProductoId(), -pedido.getCantidad());
        return nuevoPedido;
    }

    // Obtener todos los productos
    public List<ProductoDTO> obtenerProductos() {
        return msInventarioClient.obtenerTodos();
    }

    // Obtener producto por id
    public ProductoDTO obtenerProductoPorId(String productoId) {
        return msInventarioClient.obtenerPorProductoId(productoId);
    }
}