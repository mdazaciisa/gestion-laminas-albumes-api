package com.example.gestion_laminas_albunes_api.controller;

import com.example.gestion_laminas_albunes_api.request.AlbumRequest;
import com.example.gestion_laminas_albunes_api.response.AlbumResponse;
import com.example.gestion_laminas_albunes_api.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los álbumes.
 * Proporciona endpoints para operaciones CRUD.
 */
@RestController
@RequestMapping("/api/albumes")
public class AlbumController {
    
    @Autowired
    private AlbumService albumService;
    
    /**
     * Obtener todos los álbumes.
     * GET /api/albumes
     */
    @GetMapping
    public ResponseEntity<List<AlbumResponse>> obtenerTodos() {
        List<AlbumResponse> albumes = albumService.obtenerTodos();
        return ResponseEntity.ok(albumes);
    }
    
    /**
     * Obtener un álbum por ID.
     * GET /api/albumes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> obtenerPorId(@PathVariable Long id) {
        try {
            AlbumResponse album = albumService.obtenerPorId(id);
            return ResponseEntity.ok(album);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Crear un nuevo álbum.
     * POST /api/albumes
     */
    @PostMapping
    public ResponseEntity<AlbumResponse> crear(@RequestBody AlbumRequest request) {
        try {
            AlbumResponse album = albumService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(album);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Actualizar un álbum existente.
     * PUT /api/albumes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponse> actualizar(@PathVariable Long id, @RequestBody AlbumRequest request) {
        try {
            AlbumResponse album = albumService.actualizar(id, request);
            return ResponseEntity.ok(album);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Eliminar un álbum.
     * DELETE /api/albumes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            albumService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Buscar álbumes por nombre.
     * GET /api/albumes/buscar?nombre={nombre}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<AlbumResponse>> buscarPorNombre(@RequestParam String nombre) {
        List<AlbumResponse> albumes = albumService.buscarPorNombre(nombre);
        return ResponseEntity.ok(albumes);
    }
}
