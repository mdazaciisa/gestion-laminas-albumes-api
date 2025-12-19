package com.example.gestion_laminas_albunes_api.controller;

import com.example.gestion_laminas_albunes_api.model.Album;
import com.example.gestion_laminas_albunes_api.request.AlbumRequest;
import com.example.gestion_laminas_albunes_api.response.AlbumResponse;
import com.example.gestion_laminas_albunes_api.response.AlbumesResponse;
import com.example.gestion_laminas_albunes_api.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albumes")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /* Obtener todos los álbumes -> GET /api/albumes **/
    @GetMapping
    public ResponseEntity<Object> obtenerTodos() {
        AlbumesResponse respuesta = new AlbumesResponse();
        respuesta.setStatus(200);
        respuesta.setMensaje("Albumes obtenidos");
        respuesta.setDatos(albumService.obtenerTodos());

        return ResponseEntity.ok().body(respuesta);
    }

    /* Obtener un álbum por ID -> GET /api/albumes/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPorId(@PathVariable Long id) {
        Album album = albumService.obtenerId(id);
        AlbumResponse respuesta = new AlbumResponse();
        if (album != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Album obtenido");
            respuesta.setDatos(album);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Album no encontrado");
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /* Crear un nuevo álbum ->POST /api/albumes */
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody AlbumRequest request) {
        Album album = albumService.crear(request);

        AlbumResponse respuesta = new AlbumResponse();
        if (album != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Album creado");
            respuesta.setDatos(album);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Album no creado");
            return ResponseEntity.status(404).body(respuesta);
        }

    }

    /* Actualizar un álbum existente -> PUT /api/albumes/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponse> actualizar(@PathVariable Long id, @RequestBody AlbumRequest request) {
        Album album = albumService.actualizar(id, request);

        AlbumResponse respuesta = new AlbumResponse();
        if (album != null) {
            respuesta.setStatus(200);
            respuesta.setMensaje("Album actualizado");
            respuesta.setDatos(album);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("No se encontró el album de id " + id);
            return ResponseEntity.status(404).body(respuesta);
        }
    }

    /* Eliminar un álbum -> DELETE /api/albumes/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumResponse> eliminar(@PathVariable Long id) {
        AlbumResponse respuesta = new AlbumResponse();

        if (albumService.obtenerId(id) == null) {
            respuesta.setStatus(404);
            respuesta.setMensaje("El album no existe");
            return ResponseEntity.status(404).body(respuesta);
        }
        albumService.eliminar(id);
        respuesta.setStatus(200);
        respuesta.setMensaje("Album eliminado");
        return ResponseEntity.ok().body(respuesta);
    }

    /* Buscar álbumes por nombre -> GET /api/albumes/buscar?nombre={nombre} */
    @GetMapping("/buscar")
    public ResponseEntity<List<Album>> buscarPorNombre(@RequestParam String nombre) {
        List<Album> albumes = albumService.buscarPorNombre(nombre);
        return ResponseEntity.ok(albumes);
    }

    /* Obtener un álbum con estadísticas -> GET /api/albumes/{id}/estadisticas */
    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<Object> obtenerConEstadisticas(@PathVariable Long id) {
        Album album = albumService.obtenerId(id);
        AlbumResponse respuesta = new AlbumResponse();

        if (album != null) {
            Album albumConEstadisticas = albumService.estadisticasAlbum(album);

            respuesta.setStatus(200);
            respuesta.setMensaje("Álbum obtenido con estadísticas");
            respuesta.setDatos(albumConEstadisticas);
            return ResponseEntity.ok().body(respuesta);
        } else {
            respuesta.setStatus(404);
            respuesta.setMensaje("Álbum no encontrado");
            return ResponseEntity.status(404).body(respuesta);
        }
    }
}
