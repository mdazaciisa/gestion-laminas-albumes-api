package com.example.gestion_laminas_albunes_api.controller;

import com.example.gestion_laminas_albunes_api.request.LaminaRequest;
import com.example.gestion_laminas_albunes_api.request.ListaLaminasRequest;
import com.example.gestion_laminas_albunes_api.response.LaminaRepetidaResponse;
import com.example.gestion_laminas_albunes_api.response.LaminaResponse;
import com.example.gestion_laminas_albunes_api.service.LaminaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las láminas.
 * Proporciona endpoints para operaciones CRUD y funcionalidades especiales.
 */
@RestController
@RequestMapping("/api/laminas")
@CrossOrigin(origins = "*")
public class LaminaController {
    
    @Autowired
    private LaminaService laminaService;
    
    /**
     * Obtener todas las láminas de un álbum.
     * GET /api/laminas/album/{albumId}
     */
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<LaminaResponse>> obtenerPorAlbum(@PathVariable Long albumId) {
        try {
            List<LaminaResponse> laminas = laminaService.obtenerPorAlbum(albumId);
            return ResponseEntity.ok(laminas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Obtener una lámina por ID.
     * GET /api/laminas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<LaminaResponse> obtenerPorId(@PathVariable Long id) {
        try {
            LaminaResponse lamina = laminaService.obtenerPorId(id);
            return ResponseEntity.ok(lamina);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Crear una nueva lámina en un álbum.
     * POST /api/laminas/album/{albumId}
     */
    @PostMapping("/album/{albumId}")
    public ResponseEntity<LaminaResponse> crear(@PathVariable Long albumId, @RequestBody LaminaRequest request) {
        try {
            LaminaResponse lamina = laminaService.crear(albumId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(lamina);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Crear múltiples láminas de forma masiva.
     * POST /api/laminas/album/{albumId}/multiple
     */
    @PostMapping("/album/{albumId}/multiple")
    public ResponseEntity<List<LaminaResponse>> crearMultiples(@PathVariable Long albumId, @RequestBody ListaLaminasRequest request) {
        try {
            List<LaminaResponse> laminas = laminaService.crearMultiples(albumId, request.getNumeros());
            return ResponseEntity.status(HttpStatus.CREATED).body(laminas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Actualizar una lámina existente.
     * PUT /api/laminas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<LaminaResponse> actualizar(@PathVariable Long id, @RequestBody LaminaRequest request) {
        try {
            LaminaResponse lamina = laminaService.actualizar(id, request);
            return ResponseEntity.ok(lamina);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Marcar una lámina como adquirida.
     * PATCH /api/laminas/album/{albumId}/adquirir/{numero}
     */
    @PatchMapping("/album/{albumId}/adquirir/{numero}")
    public ResponseEntity<LaminaResponse> marcarAdquirida(@PathVariable Long albumId, @PathVariable Integer numero) {
        try {
            LaminaResponse lamina = laminaService.marcarAdquirida(albumId, numero);
            return ResponseEntity.ok(lamina);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Agregar una lámina repetida.
     * PATCH /api/laminas/album/{albumId}/repetida/{numero}
     */
    @PatchMapping("/album/{albumId}/repetida/{numero}")
    public ResponseEntity<LaminaResponse> agregarRepetida(@PathVariable Long albumId, @PathVariable Integer numero) {
        try {
            LaminaResponse lamina = laminaService.agregarRepetida(albumId, numero);
            return ResponseEntity.ok(lamina);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Eliminar una lámina.
     * DELETE /api/laminas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            laminaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Obtener láminas faltantes de un álbum.
     * GET /api/laminas/album/{albumId}/faltantes
     */
    @GetMapping("/album/{albumId}/faltantes")
    public ResponseEntity<List<LaminaResponse>> obtenerFaltantes(@PathVariable Long albumId) {
        try {
            List<LaminaResponse> faltantes = laminaService.obtenerFaltantes(albumId);
            return ResponseEntity.ok(faltantes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Obtener láminas repetidas de un álbum con sus cantidades.
     * GET /api/laminas/album/{albumId}/repetidas
     */
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
