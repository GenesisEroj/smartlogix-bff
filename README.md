# SmartLogix BFF (Backend For Frontend)

Intermediario entre el frontend React y los microservicios de SmartLogix.
Construido con **Spring Boot 3.3.5 + Maven**.

## Patrones de Diseño Implementados

| Patrón | Ubicación | Descripción |
|---|---|---|
| **Factory Method** | `WebClientConfig.java` | Crea instancias de `WebClient` configuradas para cada microservicio |
| **Strategy** | `InventarioService.java` / `PedidosService.java` | Encapsula la lógica de comunicación con cada microservicio de forma intercambiable |

## Arquitectura

```
Frontend (3000/3001)
      │
      ▼
  BFF (8084)          ← este proyecto
  ├── /api/inventario → ms-inventario (8082)
  └── /api/pedidos    → ms-pedidos    (8081)
```

## Requisitos

- **Java 17** (recomendado) — JDK 21+ puede causar incompatibilidades con Lombok
- Maven 3.8+
- ms-inventario corriendo en puerto **8082**
- ms-pedidos corriendo en puerto **8081**

> ⚠️ **Nota sobre JDK:** Si tienes múltiples versiones de Java instaladas, asegúrate de que `JAVA_HOME` apunte a JDK 17 antes de ejecutar Maven:
> ```powershell
> $env:JAVA_HOME = "C:\Program Files\Amazon Corretto\jdk17.0.18_9"
> ```

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

El BFF queda disponible en: `http://localhost:8084`

## Configuración

Editar `src/main/resources/application.properties`:

```properties
server.port=8084
spring.application.name=smartlogix-bff

# URLs de los microservicios
ms.inventario.url=http://localhost:8082
ms.pedidos.url=http://localhost:8081

# CORS - permite llamadas desde el frontend React
cors.allowed-origins=http://localhost:3000,http://localhost:3001
```

## Ejecutar Pruebas

```bash
mvn test
```

## Endpoints disponibles

### Inventario (`/api/inventario`)
| Método | URL | Descripción |
|---|---|---|
| GET | `/api/inventario` | Listar todos los productos |
| GET | `/api/inventario/{id}` | Obtener producto por ID |
| POST | `/api/inventario` | Crear producto |
| PUT | `/api/inventario/{id}` | Actualizar producto |
| DELETE | `/api/inventario/{id}` | Eliminar producto |

### Pedidos (`/api/pedidos`)
| Método | URL | Descripción |
|---|---|---|
| GET | `/api/pedidos` | Listar todos los pedidos |
| GET | `/api/pedidos/{id}` | Obtener pedido por ID |
| POST | `/api/pedidos` | Crear pedido |
| PATCH | `/api/pedidos/{id}/estado` | Cambiar estado del pedido |
| DELETE | `/api/pedidos/{id}` | Eliminar pedido |
