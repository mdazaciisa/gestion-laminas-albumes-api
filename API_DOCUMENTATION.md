# API de Gestión de Láminas de Álbumes

Sistema de gestión para coleccionistas de láminas de álbumes desarrollado con Spring Boot y Java 21.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## Configuración de la Base de Datos

Antes de ejecutar la aplicación, debes crear la base de datos en PostgreSQL:

```sql
CREATE DATABASE gestion_laminas_db;
```

La configuración predeterminada en `application.properties`:
- **URL:** `jdbc:postgresql://localhost:5432/gestion_laminas_db`
- **Usuario:** `admin`
- **Contraseña:** `admin`
- **Puerto:** `8080`

## Ejecutar el Proyecto

```bash
./mvnw spring-boot:run
```

O en Windows:
```bash
mvnw.cmd spring-boot:run
```

## Estructura del Proyecto

```
src/main/java/com/example/gestion_laminas_albunes_api/
├── controller/      # Controladores REST
├── model/          # Entidades JPA
├── repository/     # Repositorios de datos
├── request/        # DTOs para peticiones
├── response/       # DTOs para respuestas
└── service/        # Lógica de negocio
```

## Modelo de Datos

### Album
- `id`: Identificador único (Long)
- `nombre`: Nombre del álbum (String)
- `imagen`: URL de la imagen (String)
- `fechaLanzamiento`: Fecha de lanzamiento (LocalDate)
- `tipoLaminas`: Tipo de láminas (String)
- `totalLaminas`: Total de láminas en el álbum (Integer)
- `descripcion`: Descripción del álbum (String)

### Lamina
- `id`: Identificador único (Long)
- `numero`: Número de la lámina en el álbum (Integer)
- `nombre`: Nombre de la lámina (String)
- `imagen`: URL de la imagen (String)
- `adquirida`: Estado de adquisición (Boolean)
- `cantidadRepetidas`: Cantidad de repetidas (Integer)
- `albumId`: ID del álbum al que pertenece (Long)

---

# Documentación de Endpoints

## ÁLBUMES

### 1. Obtener todos los álbumes
**GET** `/api/albumes`

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Mundial Qatar 2022",
    "imagen": "https://ejemplo.com/imagen.jpg",
    "fechaLanzamiento": "2022-10-01",
    "tipoLaminas": "deportes",
    "totalLaminas": 640,
    "descripcion": "Álbum oficial del Mundial",
    "laminasAdquiridas": 320,
    "laminasFaltantes": 320,
    "porcentajeCompletado": 50.0
  }
]
```

### 2. Obtener un álbum por ID
**GET** `/api/albumes/{id}`

**Parámetros:**
- `id` (path): ID del álbum

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Mundial Qatar 2022",
  "imagen": "https://ejemplo.com/imagen.jpg",
  "fechaLanzamiento": "2022-10-01",
  "tipoLaminas": "deportes",
  "totalLaminas": 640,
  "descripcion": "Álbum oficial del Mundial",
  "laminasAdquiridas": 320,
  "laminasFaltantes": 320,
  "porcentajeCompletado": 50.0
}
```

### 3. Crear un nuevo álbum
**POST** `/api/albumes`

**Request Body:**
```json
{
  "nombre": "Mundial Qatar 2022",
  "imagen": "https://ejemplo.com/imagen.jpg",
  "fechaLanzamiento": "2022-10-01",
  "tipoLaminas": "deportes",
  "totalLaminas": 640,
  "descripcion": "Álbum oficial del Mundial"
}
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "nombre": "Mundial Qatar 2022",
  "imagen": "https://ejemplo.com/imagen.jpg",
  "fechaLanzamiento": "2022-10-01",
  "tipoLaminas": "deportes",
  "totalLaminas": 640,
  "descripcion": "Álbum oficial del Mundial",
  "laminasAdquiridas": 0,
  "laminasFaltantes": 640,
  "porcentajeCompletado": 0.0
}
```

Nota: Para crear varias láminas de un álbum usa el endpoint de carga masiva: POST /api/laminas/album/{albumId}/multiple con `{ "numeros": [1,2,3,...] }`.

### 4. Actualizar un álbum
**PUT** `/api/albumes/{id}`

**Parámetros:**
- `id` (path): ID del álbum

**Request Body:**
```json
{
  "nombre": "Mundial Qatar 2022 - Actualizado",
  "imagen": "https://ejemplo.com/imagen-nueva.jpg",
  "fechaLanzamiento": "2022-10-01",
  "tipoLaminas": "deportes",
  "totalLaminas": 640,
  "descripcion": "Álbum oficial del Mundial - Actualizado"
}
```

**Respuesta (200 OK):** Mismo formato que la creación.

### 5. Eliminar un álbum
**DELETE** `/api/albumes/{id}`

**Parámetros:**
- `id` (path): ID del álbum

**Respuesta (204 No Content):** Sin contenido.

### 6. Buscar álbumes por nombre
**GET** `/api/albumes/buscar?nombre={texto}`

**Parámetros:**
- `nombre` (query): Texto a buscar

**Respuesta (200 OK):** Lista de álbumes que coinciden con el criterio.

---

## LÁMINAS

### 1. Obtener todas las láminas de un álbum
**GET** `/api/laminas/album/{albumId}`

**Parámetros:**
- `albumId` (path): ID del álbum

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "numero": 1,
    "nombre": "Lámina 1",
    "imagen": null,
    "adquirida": false,
    "cantidadRepetidas": 0,
    "albumId": 1
  }
]
```

### 2. Obtener una lámina por ID
**GET** `/api/laminas/{id}`

**Parámetros:**
- `id` (path): ID de la lámina

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "numero": 1,
  "nombre": "Lámina 1",
  "imagen": null,
  "adquirida": false,
  "cantidadRepetidas": 0,
  "albumId": 1
}
```

### 3. Crear una lámina individual
**POST** `/api/laminas/album/{albumId}`

**Parámetros:**
- `albumId` (path): ID del álbum

**Request Body:**
```json
{
  "numero": 10,
  "nombre": "Lionel Messi",
  "imagen": "https://ejemplo.com/messi.jpg",
  "adquirida": true,
  "cantidadRepetidas": 2
}
```

**Respuesta (201 Created):**
```json
{
  "id": 10,
  "numero": 10,
  "nombre": "Lionel Messi",
  "imagen": "https://ejemplo.com/messi.jpg",
  "adquirida": true,
  "cantidadRepetidas": 2,
  "albumId": 1
}
```

Nota: No se permite crear una lámina con un `numero` que ya exista en el mismo álbum. La petición será rechazada.

### 4. Crear múltiples láminas (carga masiva)
**POST** `/api/laminas/album/{albumId}/multiple`

**Parámetros:**
- `albumId` (path): ID del álbum

**Request Body:**
```json
{
  "numeros": [1, 2, 3, 4, 5, 10, 15, 20]
}
```

**Respuesta (201 Created):** Lista de láminas creadas.

**Nota:** Este endpoint solo crea láminas que no existen. Si una lámina ya existe, la omite.
En caso de enviar números duplicados existentes, no se crearán registros duplicados.

### 5. Actualizar una lámina
**PUT** `/api/laminas/{id}`

**Parámetros:**
- `id` (path): ID de la lámina

**Request Body:**
```json
{
  "numero": 10,
  "nombre": "Lionel Messi - Actualizado",
  "imagen": "https://ejemplo.com/messi-nuevo.jpg",
  "adquirida": true,
  "cantidadRepetidas": 3
}
```

**Respuesta (200 OK):** Lámina actualizada.

### 6. Marcar lámina como adquirida
**PATCH** `/api/laminas/album/{albumId}/adquirir/{numero}`

**Parámetros:**
- `albumId` (path): ID del álbum
- `numero` (path): Número de la lámina

**Respuesta (200 OK):** Lámina con estado `adquirida: true`.

### 7. Agregar lámina repetida
**PATCH** `/api/laminas/album/{albumId}/repetida/{numero}`

**Parámetros:**
- `albumId` (path): ID del álbum
- `numero` (path): Número de la lámina

**Respuesta (200 OK):** Lámina con `cantidadRepetidas` incrementada.

### 8. Eliminar una lámina (soft)
**DELETE** `/api/laminas/{id}`

**Parámetros:**
- `id` (path): ID de la lámina

**Respuesta (204 No Content):** La lámina no se elimina físicamente; se marca con `adquirida: false`.

### 9. Obtener láminas faltantes
**GET** `/api/laminas/album/{albumId}/faltantes`

**Parámetros:**
- `albumId` (path): ID del álbum

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "numero": 1,
    "nombre": "Lámina 1",
    "imagen": null,
    "adquirida": false,
    "cantidadRepetidas": 0,
    "albumId": 1
  }
]
```

**Nota:** Devuelve solo las láminas con `adquirida: false`.

### 10. Obtener láminas repetidas
**GET** `/api/laminas/album/{albumId}/repetidas`

**Parámetros:**
- `albumId` (path): ID del álbum

**Respuesta (200 OK):**
```json
[
  {
    "id": 10,
    "numero": 10,
    "nombre": "Lionel Messi",
    "cantidadRepetidas": 3
  }
]
```

**Nota:** Devuelve solo las láminas con `cantidadRepetidas > 0`.

---

## Ejemplos de Uso con cURL

### Crear un álbum:
```bash
curl -X POST http://localhost:8080/api/albumes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Mundial Qatar 2022",
    "tipoLaminas": "deportes",
    "totalLaminas": 640
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

### Agregar láminas múltiples:
```bash
curl -X POST http://localhost:8080/api/laminas/album/1/multiple \
  -H "Content-Type: application/json" \
  -d '{
    "numeros": [1, 2, 3, 4, 5]
  }'
```

---

## Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Operación sin contenido (por ejemplo, marcar como no adquirida)
- **400 Bad Request**: Datos inválidos en la petición
- **404 Not Found**: Recurso no encontrado

---

## Características Especiales

1. **Sin auto-creación**: La creación del álbum no genera láminas automáticamente.

2. **Carga masiva**: Puedes agregar múltiples láminas con una sola petición.

3. **Estadísticas del álbum**: Cada álbum devuelve estadísticas de completación (láminas adquiridas, faltantes y porcentaje).

4. **Consultas optimizadas**: Endpoints específicos para obtener solo láminas faltantes o repetidas.

---

## Próximos Pasos

Para realizar las pruebas:

1. Asegúrate de que PostgreSQL esté corriendo
2. Crea la base de datos `gestion_laminas_db`
3. Ejecuta la aplicación con `./mvnw spring-boot:run`
4. Usa Postman, cURL o cualquier cliente HTTP para probar los endpoints
5. Documenta los resultados con screenshots para tu informe
