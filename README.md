# SmartLogix - Backend For Frontend (BFF)

## Descripción
Componente intermediario entre el frontend y los microservicios de SmartLogix.
Consolida las respuestas de MS Pedidos y MS Inventario, optimizando la
comunicación para la interfaz de usuario.

## Tecnologías
- Java 17
- Spring Boot 3.5.15
- Spring Web (RestTemplate)
- Lombok
- Maven

## Patrones de Diseño Implementados
- **BFF Pattern**: Intermediario entre frontend y microservicios
- **DTO**: Transferencia de datos entre capas
- **Singleton (Bean)**: Gestión de RestTemplate como bean compartido

## Estructura del Proyecto

src/
├── main/
│   ├── java/com/smartlogix/bff/
│   │   ├── controller/    # BffController
│   │   ├── service/       # BffService
│   │   ├── client/        # MsPedidosClient, MsInventarioClient
│   │   ├── dto/           # PedidoDTO, ProductoDTO
│   │   └── config/        # AppConfig (RestTemplate Bean)
│   └── resources/
│       └── application.properties

## Requisitos
- Java 17+
- Maven 3.9+
- MS Pedidos corriendo en puerto 8081
- MS Inventario corriendo en puerto 8082

## Configuración
Edita `src/main/resources/application.properties`:
```properties
server.port=8080
spring.application.name=smartlogix-bff
ms.pedidos.url=http://localhost:8081/api/pedidos
ms.inventario.url=http://localhost:8082/api/inventario
```

## Instalación y Ejecución
```bash
# Clonar el repositorio
git clone https://github.com/GenesisEroj/smartlogix-bff

# Entrar al directorio
cd smartlogix-bff

# Ejecutar con Maven
mvn spring-boot:run

# O con Maven Wrapper
./mvnw spring-boot:run
```

## Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/bff/pedidos | Obtener todos los pedidos |
| GET | /api/bff/pedidos/{id} | Obtener pedido por ID |
| POST | /api/bff/pedidos | Crear nuevo pedido |
| GET | /api/bff/productos | Obtener todos los productos |
| GET | /api/bff/productos/{productoId} | Obtener producto por ID |

## Equipo
- Genesis Eroj
- Francisco Monsalve

**DSY1106 - Desarrollo Fullstack III**
