# SmartLogix BFF (Backend For Frontend)

Intermediario entre el frontend React y los microservicios de SmartLogix.
Construido con **Spring Boot 3 + Maven**.

## Patrones de Diseño Implementados

| Patrón | Ubicación | Descripción |
|---|---|---|
| **Factory Method** | `WebClientConfig.java` | Crea instancias de `WebClient` configuradas para cada microservicio |
| **Strategy** | `InventarioService.java` / `PedidosService.java` | Encapsula la lógica de comunicación con cada microservicio de forma intercambiable |

## Arquitectura

```
Frontend (3000)
      │
      ▼
  BFF (8080)          ← este proyecto
  ├── /api/inventario → ms-inventario (8081)
  └── /api/pedidos    → ms-pedidos    (8082)
```

## Requisitos

- Java 17+
- Maven 3.8+
- ms-inventario corriendo en puerto 8081
- ms-pedidos corriendo en puerto 8082

## Instalación y Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/GenesisEroj/smartlogix-bff.git
cd smartlogix-bff

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

El BFF queda disponible en: `http://localhost:8080`

## Configuración

Editar `src/main/resources/application.properties`:

```properties
server.port=8080
ms.inventario.url=http://localhost:8081
ms.pedidos.url=http://localhost:8082
cors.allowed-origins=http://localhost:3000
```

## Ejecutar Pruebas

```bash
mvn test
```

## Endpoints disponibles

### Inventario
| Método | URL | Descripción |
|---|---|---|
| GET | `/api/inventario/productos` | Listar productos |
| GET | `/api/inventario/productos/{id}` | Obtener producto |
| POST | `/api/inventario/productos` | Crear producto |
| PUT | `/api/inventario/productos/{id}` | Actualizar producto |
| DELETE | `/api/inventario/productos/{id}` | Eliminar producto |

### Pedidos
| Método | URL | Descripción |
|---|---|---|
| GET | `/api/pedidos` | Listar pedidos |
| GET | `/api/pedidos/{id}` | Obtener pedido |
| POST | `/api/pedidos` | Crear pedido |
| PATCH | `/api/pedidos/{id}/estado` | Cambiar estado |
| DELETE | `/api/pedidos/{id}` | Eliminar pedido |
