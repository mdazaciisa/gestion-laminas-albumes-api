package com.example.gestion_laminas_albunes_api.service;

import com.example.gestion_laminas_albunes_api.model.Album;
import com.example.gestion_laminas_albunes_api.model.Lamina;
import com.example.gestion_laminas_albunes_api.repository.AlbumRepository;
import com.example.gestion_laminas_albunes_api.repository.LaminaRepository;
import com.example.gestion_laminas_albunes_api.request.AlbumRequest;
import com.example.gestion_laminas_albunes_api.response.AlbumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de los álbumes.
 */
@Service
public class AlbumService {
    
    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private LaminaRepository laminaRepository;
    
    /**
     * Obtener todos los álbumes.
     */
    public List<AlbumResponse> obtenerTodos() {
        return albumRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener un álbum por ID.
     */
    public AlbumResponse obtenerPorId(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado con ID: " + id));
        return convertirAResponse(album);
    }
    
    /**
     * Crear un nuevo álbum.
     */
    @Transactional
    public AlbumResponse crear(AlbumRequest request) {
        Album album = new Album();
        album.setNombre(request.getNombre());
        album.setImagen(request.getImagen());
        album.setFechaLanzamiento(request.getFechaLanzamiento());
        album.setTipoLaminas(request.getTipoLaminas());
        album.setTotalLaminas(request.getTotalLaminas());
        album.setDescripcion(request.getDescripcion());
        
        Album albumGuardado = albumRepository.save(album);
        // Nota: La creación masiva de láminas se realiza mediante el endpoint específico
        // POST /api/laminas/album/{albumId}/multiple
        return convertirAResponse(albumGuardado);
    }
    
    /**
     * Actualizar un álbum existente.
     */
    @Transactional
    public AlbumResponse actualizar(Long id, AlbumRequest request) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado con ID: " + id));
        
        album.setNombre(request.getNombre());
        album.setImagen(request.getImagen());
        album.setFechaLanzamiento(request.getFechaLanzamiento());
        album.setTipoLaminas(request.getTipoLaminas());
        album.setTotalLaminas(request.getTotalLaminas());
        album.setDescripcion(request.getDescripcion());
        
        Album albumActualizado = albumRepository.save(album);
        return convertirAResponse(albumActualizado);
    }
    
    /**
     * Eliminar un álbum.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Álbum no encontrado con ID: " + id);
        }
        albumRepository.deleteById(id);
    }
    
    /**
     * Buscar álbumes por nombre.
     */
    public List<AlbumResponse> buscarPorNombre(String nombre) {
        return albumRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Convertir entidad Album a AlbumResponse.
     */
    private AlbumResponse convertirAResponse(Album album) {
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setNombre(album.getNombre());
        response.setImagen(album.getImagen());
        response.setFechaLanzamiento(album.getFechaLanzamiento());
        response.setTipoLaminas(album.getTipoLaminas());
        response.setTotalLaminas(album.getTotalLaminas());
        response.setDescripcion(album.getDescripcion());
        
        // Calcular estadísticas
        List<Lamina> laminas = laminaRepository.findByAlbumId(album.getId());
        long adquiridas = laminas.stream().filter(Lamina::getAdquirida).count();
        response.setLaminasAdquiridas((int) adquiridas);
        response.setLaminasFaltantes(laminas.size() - (int) adquiridas);
        
        if (laminas.size() > 0) {
            double porcentaje = (adquiridas * 100.0) / laminas.size();
            response.setPorcentajeCompletado(Math.round(porcentaje * 100.0) / 100.0);
        } else {
            response.setPorcentajeCompletado(0.0);
        }
        
        return response;
    }
}
