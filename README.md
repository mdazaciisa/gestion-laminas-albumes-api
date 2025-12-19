# 📚 Sistema de Gestión de Láminas de Álbumes

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)

Sistema desarrollado con Spring Boot para la gestión eficiente de colecciones de láminas de álbumes. Ideal para coleccionistas que desean llevar un control detallado de sus álbumes, láminas adquiridas, faltantes y repetidas.

## 🎯 Características

- ✅ **Gestión de Álbumes**: Crear, leer, actualizar y eliminar álbumes
- ✅ **Gestión de Láminas**: CRUD completo con carga individual y masiva
- ✅ **Control de Estado**: Marcar láminas como adquiridas
- ✅ **Láminas Repetidas**: Llevar control de cantidad de repetidas
- ✅ **Consultas Especiales**: Endpoints para láminas faltantes y repetidas
- ✅ **Estadísticas**: Porcentaje de completación automático por álbum
- ✅ **API REST**: Endpoints bien documentados y fáciles de consumir
- ✅ **Base de Datos**: Persistencia con PostgreSQL y JPA

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 21 o superior
- PostgreSQL 12 o superior
- Maven 3.6 o superior

### Instalación

1. **Clonar o descargar el proyecto**

2. **Crear la base de datos en PostgreSQL:**
```sql
CREATE DATABASE gestion_laminas_db;
```

3. **Configurar credenciales** (opcional)

   Editar `src/main/resources/application.properties`:
```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

4. **Ejecutar la aplicación:**
```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

5. **Acceder a la API:**
```
http://localhost:8080/api/albumes
```

## 📖 Documentación

| Documento | Descripción |
|-----------|-------------|
| [API_DOCUMENTATION.md](API_DOCUMENTATION.md) | Documentación completa de todos los endpoints |
| [database_setup.sql](database_setup.sql) | Script SQL con consultas útiles |

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/example/gestion_laminas_albunes_api/
│
├── 📁 controller/          # Controladores REST
│   ├── AlbumController.java
│   └── LaminaController.java
│
├── 📁 model/              # Entidades JPA
│   ├── Album.java
│   └── Lamina.java
│
├── 📁 repository/         # Repositorios de datos
│   ├── AlbumRepository.java
│   └── LaminaRepository.java
│
├── 📁 request/            # DTOs de peticiones
│   ├── AlbumRequest.java
│   ├── LaminaRequest.java
│   └── ListaLaminasRequest.java
│
├── 📁 response/           # DTOs de respuestas
│   ├── AlbumResponse.java
│   ├── LaminaResponse.java
│   └── LaminaRepetidaResponse.java
│
└── 📁 service/            # Lógica de negocio
    ├── AlbumService.java
    └── LaminaService.java
```

## 🔌 Endpoints Principales

### Álbumes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/albumes` | Obtener todos los álbumes |
| GET | `/api/albumes/{id}` | Obtener un álbum por ID |
| POST | `/api/albumes` | Crear un nuevo álbum |
| PUT | `/api/albumes/{id}` | Actualizar un álbum |
| DELETE | `/api/albumes/{id}` | Eliminar un álbum |
| GET | `/api/albumes/buscar?nombre={texto}` | Buscar álbumes por nombre |

### Láminas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/laminas/album/{albumId}` | Obtener láminas de un álbum |
| GET | `/api/laminas/{id}` | Obtener una lámina por ID |
| POST | `/api/laminas/album/{albumId}` | Crear una lámina |
| POST | `/api/laminas/album/{albumId}/multiple` | Crear múltiples láminas |
| PUT | `/api/laminas/{id}` | Actualizar una lámina |
| PATCH | `/api/laminas/album/{albumId}/adquirir/{numero}` | Marcar como adquirida |
| PATCH | `/api/laminas/album/{albumId}/repetida/{numero}` | Agregar repetida |
| DELETE | `/api/laminas/{id}` | Eliminar una lámina |
| GET | `/api/laminas/album/{albumId}/faltantes` | Obtener láminas faltantes |
| GET | `/api/laminas/album/{albumId}/repetidas` | Obtener láminas repetidas |

## 💡 Ejemplo de Uso

### Crear un álbum:
```bash
curl -X POST http://localhost:8080/api/albumes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Mundial Qatar 2022",
    "tipoLaminas": "deportes",
    "totalLaminas": 640,
    "descripcion": "Álbum oficial del Mundial"
  }'
```

### Marcar lámina como adquirida:
```bash
curl -X PATCH http://localhost:8080/api/laminas/album/1/adquirir/10
```

### Obtener láminas faltantes:
```bash
curl http://localhost:8080/api/laminas/album/1/faltantes
```

## 📊 Modelo de Datos

### Album
- `id`: Long (PK)
- `nombre`: String
- `imagen`: String
- `fechaLanzamiento`: LocalDate
- `tipoLaminas`: String
- `totalLaminas`: Integer
- `descripcion`: String

### Lamina
- `id`: Long (PK)
- `numero`: Integer
- `nombre`: String
- `imagen`: String
- `adquirida`: Boolean
- `cantidadRepetidas`: Integer
- `albumId`: Long (FK)

**Relación:** Un álbum tiene muchas láminas (1:N)

## 🛠️ Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación
- **Spring Boot 4.0.1**: Framework principal
- **Spring Data JPA**: Capa de persistencia
- **PostgreSQL**: Base de datos relacional
- **Lombok**: Reducción de código boilerplate (@Data)
- **Maven**: Gestión de dependencias
- **Hibernate**: ORM para JPA
- **SpringDoc OpenAPI**: Documentación automática de la API (Swagger UI)

## ✨ Funcionalidades Especiales

### 1. Carga de Láminas sin Auto-Creación
La creación de un álbum no genera láminas automáticamente. Para cargar láminas, utiliza:
- El endpoint de creación individual: `POST /api/laminas/album/{albumId}`
- El endpoint de carga múltiple: `POST /api/laminas/album/{albumId}/multiple` con una lista de números

### 2. Carga Masiva
Endpoint especial para agregar múltiples láminas de una sola vez mediante una lista de números.

### 3. Estadísticas por álbum
Cada respuesta de álbum incluye:
- Cantidad de láminas adquiridas
- Cantidad de láminas faltantes
- Porcentaje de completación

Endpoint:
`GET /api/albumes/{id}/estadisticas`

### 4. Consultas Optimizadas
Endpoints específicos para obtener solo las láminas faltantes o solo las repetidas, evitando transferir datos innecesarios.

## 🔗 Documentación Swagger
La API cuenta con documentación interactiva generada con Swagger UI, donde puedes explorar todos los endpoints, probar solicitudes y ver los modelos.

[Acceder a Swagger UI](http://localhost:8080/swagger-ui/index.html)

⚠️ Nota: El link anterior funciona cuando la aplicación se está ejecutando localmente.
Si la API está desplegada en un servidor, reemplaza localhost:8080 por la URL correspondiente.

## 👥 Roles y responsabilidades del equipo
**Felipe Aguirre**
- Diseño e implementación de la arquitectura base del proyecto.
- Desarrollo completo del CRUD de Álbumes y Láminas.
- Modelado de entidades y relaciones JPA/Hibernate.
- Configuración y estructura de la base de datos PostgreSQL.
- Creación y mantenimiento de la colección Postman con pruebas de todos los endpoints.

**Marcela Daza**
- Implementación del manejo de errores y validaciones de la API
(IDs inexistentes, recursos no encontrados, duplicados y respuestas controladas).
- Ajustes en la lógica de negocio para asegurar la integridad de los datos.
- Implementación de endpoints especiales (faltantes, repetidas y control de estados).
- Integración y configuración de la documentación Swagger / OpenAPI.
- Revisión de respuestas JSON y estandarización de mensajes de la API.

## 📝 Notas Importantes

- Las tablas se crean automáticamente con Hibernate (ddl-auto=update)
- Al eliminar un álbum, se eliminan todas sus láminas (cascade)
- No se pueden crear láminas duplicadas (mismo número en el mismo álbum)
- Todos los endpoints devuelven JSON
- El campo `totalLaminas` representa el total teórico del álbum.
- Las láminas se crean explícitamente mediante endpoints individuales o de carga masiva.
- Las láminas faltantes corresponden a aquellas registradas en el sistema cuyo estado es `adquirida = false`.

## 🤝 Contribuciones

Este proyecto fue desarrollado como proyecto final del curso de Spring Boot.

## 📄 Licencia

Este proyecto es de código abierto y está disponible para uso educativo.

## 👨‍💻 Autor

Desarrollado con ❤️ usando Spring Boot y Java 21