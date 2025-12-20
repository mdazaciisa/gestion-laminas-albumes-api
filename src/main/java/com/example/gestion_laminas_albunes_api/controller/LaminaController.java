package com.example.gestion_laminas_albunes_api.controller;

import com.example.gestion_laminas_albunes_api.model.Lamina;
import com.example.gestion_laminas_albunes_api.request.LaminaRequest;
import com.example.gestion_laminas_albunes_api.request.ListaLaminasRequest;
import com.example.gestion_laminas_albunes_api.response.LaminaRepetidaResponse;
import com.example.gestion_laminas_albunes_api.response.LaminaResponse;
import com.example.gestion_laminas_albunes_api.response.LaminasResponse;
import com.example.gestion_laminas_albunes_api.service.LaminaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laminas")
public class LaminaController {

    @Autowired
    private LaminaService laminaService;

    /* Obtener todas las láminas de un álbum -> GET /api/laminas/album/{albumId} */
    @GetMapping("/album/{albumId}")
    public ResponseEntity<Object> obtenerPorAlbum(@PathVariable Long albumId) {
        List<Lamina> laminas = laminaService.obtenerPorAlbum(albumId);
        LaminasResponse respuesta = new LaminasResponse();

        if (laminas == null) {
            respuesta.setStatus(404);
            respuesta.setMensaje("No se encontró el álbum con el ID " + albumId);
            respuesta.setDatos(null);
            return ResponseEntity.status(404).body(respuesta);
        }
        respuesta.setStatus(200);
        respuesta.setMensaje("Laminas obtenidas");
        respuesta.setDatos(laminaService.obtenerPorAlbum(albumId));

        return ResponseEntity.ok().body(respuesta);
    }

    /* Obtener una lámina por ID -> GET /api/laminas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPorId(@PathVariable Long id) {
        Lamina lamina = laminaService.obtenerPorId(id);
        LaminaResponse respuesta = new LaminaResponse();

        if (lamina != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Lámina obtenida");
            respuesta.setDatos(lamina);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina de id " + id + " no encontrada");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /* Crear una nueva lámina en un álbum -> POST /api/laminas/album/{albumId} */
    @PostMapping("/album/{albumId}")
    public ResponseEntity<Object> crear(@PathVariable Long albumId, @RequestBody LaminaRequest request) {
        Lamina lamina = laminaService.crear(albumId, request);

        LaminaResponse respuesta = new LaminaResponse();
        if (lamina != null) {
            respuesta.setStatus(201);
            respuesta.setMensaje("Lámina creada");
            respuesta.setDatos(lamina);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina no creada");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /*
     * Crear múltiples láminas de forma masiva -> POST
     * /api/laminas/album/{albumId}/multiple
     */
    @PostMapping("/album/{albumId}/multiple")
    public ResponseEntity<List<Lamina>> crearMultiples(@PathVariable Long albumId,
            @RequestBody ListaLaminasRequest request) {
        List<Lamina> laminas = laminaService.crearMultiples(albumId, request.getNumeros());
        return new ResponseEntity<>(laminas, HttpStatus.CREATED);
    }

    /* Actualizar una lámina existente -> PUT /api/laminas/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<LaminaResponse> actualizar(@PathVariable Long id, @RequestBody LaminaRequest request) {
        Lamina lamina = laminaService.actualizar(id, request);

        LaminaResponse respuesta = new LaminaResponse();
        if (lamina != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Lámina actualizada");
            respuesta.setDatos(lamina);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina no fue actualizada. El ID no existe o el número de lámina ya existe en el álbum");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /*Marcar una lámina como adquirida ->PATCH
     * /api/laminas/album/{albumId}/adquirir/{numero}*/
    @PatchMapping("/album/{albumId}/adquirir/{numero}")
    public ResponseEntity<LaminaResponse> marcarAdquirida(@PathVariable Long albumId, @PathVariable Integer numero) {
        Lamina lamina = laminaService.marcarAdquirida(albumId, numero);
        
        LaminaResponse respuesta = new LaminaResponse();
        if(lamina != null){
            respuesta.setStatus(200);
            respuesta.setMensaje("Lámina número " + numero + " del álbum " + albumId + " marcada como adquirida");
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina de id " + numero + " no encontrada");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /*Agregar una lámina repetida -> PATCH
     * /api/laminas/album/{albumId}/repetida/{numero}*/
    @PatchMapping("/album/{albumId}/repetida/{numero}")
    public ResponseEntity<Object> agregarRepetida(@PathVariable Long albumId, @PathVariable Integer numero) {
        Lamina lamina = laminaService.agregarRepetida(albumId, numero);

        LaminaResponse respuesta = new LaminaResponse();
        if (lamina != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Lámina repetida agregada");
            respuesta.setDatos(lamina);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina de id " + numero + " no encontrada");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /* Eliminar una lámina -> DELETE /api/laminas/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<LaminaResponse> eliminar(@PathVariable Long id) {
        LaminaResponse respuesta = new LaminaResponse();

        if (laminaService.obtenerPorId(id) == null) {
            respuesta.setStatus(404);
            respuesta.setMensaje("Lámina de id " + id + " no encontrada");
            return ResponseEntity.status(404).body(respuesta);
        }
        laminaService.eliminar(id);
        respuesta.setStatus(200);
        respuesta.setMensaje("Lámina eliminada");
        return ResponseEntity.ok().body(respuesta);
    }

    /*Obtener láminas faltantes de un álbum -> GET
     * /api/laminas/album/{albumId}/faltantes*/
    @GetMapping("/album/{albumId}/faltantes")
    public ResponseEntity<List<Lamina>> obtenerFaltantes(@PathVariable Long albumId) {
        try {
            List<Lamina> faltantes = laminaService.obtenerFaltantes(albumId);
            return ResponseEntity.ok(faltantes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*Obtener láminas repetidas de un álbum con sus cantidades -> GET
     * /api/laminas/album/{albumId}/repetidas*/
    @GetMapping("/album/{albumId}/repetidas")
    public ResponseEntity<List<LaminaRepetidaResponse>> obtenerRepetidas(@PathVariable Long albumId) {
        try {
            List<LaminaRepetidaResponse> repetidas = laminaService.obtenerRepetidas(albumId);
            return ResponseEntity.ok(repetidas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
