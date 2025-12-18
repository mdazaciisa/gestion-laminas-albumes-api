package com.example.gestion_laminas_albunes_api.service;

import com.example.gestion_laminas_albunes_api.model.Album;
import com.example.gestion_laminas_albunes_api.model.Lamina;
import com.example.gestion_laminas_albunes_api.repository.AlbumRepository;
import com.example.gestion_laminas_albunes_api.repository.LaminaRepository;
import com.example.gestion_laminas_albunes_api.request.LaminaRequest;
import com.example.gestion_laminas_albunes_api.response.LaminaRepetidaResponse;
import com.example.gestion_laminas_albunes_api.response.LaminaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de las láminas.
 */
@Service
public class LaminaService {
    
    @Autowired
    private LaminaRepository laminaRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    /**
     * Obtener todas las láminas de un álbum.
     */
    public List<LaminaResponse> obtenerPorAlbum(Long albumId) {
        validarAlbumExiste(albumId);
        return laminaRepository.findByAlbumId(albumId).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener una lámina por ID.
     */
    public LaminaResponse obtenerPorId(Long id) {
        Lamina lamina = laminaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lámina no encontrada con ID: " + id));
        return convertirAResponse(lamina);
    }
    
    /**
     * Crear una nueva lámina en un álbum.
     */
    @Transactional
    public LaminaResponse crear(Long albumId, LaminaRequest request) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado con ID: " + albumId));
        
        // Verificar si ya existe una lámina con ese número en el álbum
        Lamina laminaExistente = laminaRepository.findByAlbumIdAndNumero(albumId, request.getNumero());
        if (laminaExistente != null) {
            throw new RuntimeException("Ya existe una lámina con el número " + request.getNumero() + " en este álbum");
        }
        
        Lamina lamina = new Lamina();
        lamina.setNumero(request.getNumero());
        lamina.setNombre(request.getNombre());
        lamina.setImagen(request.getImagen());
        lamina.setAdquirida(request.getAdquirida() != null ? request.getAdquirida() : false);
        lamina.setCantidadRepetidas(request.getCantidadRepetidas() != null ? request.getCantidadRepetidas() : 0);
        lamina.setAlbum(album);
        
        Lamina laminaGuardada = laminaRepository.save(lamina);
        return convertirAResponse(laminaGuardada);
    }
    
    /**
     * Crear múltiples láminas a partir de una lista de números.
     */
    @Transactional
    public List<LaminaResponse> crearMultiples(Long albumId, List<Integer> numeros) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado con ID: " + albumId));
        
        List<Lamina> laminasCreadas = numeros.stream()
                .filter(numero -> laminaRepository.findByAlbumIdAndNumero(albumId, numero) == null)
                .map(numero -> {
                    Lamina lamina = new Lamina();
                    lamina.setNumero(numero);
                    lamina.setNombre("Lámina " + numero);
                    lamina.setAdquirida(false);
                    lamina.setCantidadRepetidas(0);
                    lamina.setAlbum(album);
                    return laminaRepository.save(lamina);
                })
                .collect(Collectors.toList());
        
        return laminasCreadas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualizar una lámina existente.
     */
    @Transactional
    public LaminaResponse actualizar(Long id, LaminaRequest request) {
        Lamina lamina = laminaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lámina no encontrada con ID: " + id));
        
        if (request.getNumero() != null) lamina.setNumero(request.getNumero());
        if (request.getNombre() != null) lamina.setNombre(request.getNombre());
        if (request.getImagen() != null) lamina.setImagen(request.getImagen());
        if (request.getAdquirida() != null) lamina.setAdquirida(request.getAdquirida());
        if (request.getCantidadRepetidas() != null) lamina.setCantidadRepetidas(request.getCantidadRepetidas());
        
        Lamina laminaActualizada = laminaRepository.save(lamina);
        return convertirAResponse(laminaActualizada);
    }
    
    /**
     * Marcar una lámina como adquirida.
     */
    @Transactional
    public LaminaResponse marcarAdquirida(Long albumId, Integer numero) {
        Lamina lamina = laminaRepository.findByAlbumIdAndNumero(albumId, numero);
        if (lamina == null) {
            throw new RuntimeException("Lámina no encontrada con número " + numero + " en el álbum " + albumId);
        }
        
        lamina.setAdquirida(true);
        Lamina laminaActualizada = laminaRepository.save(lamina);
        return convertirAResponse(laminaActualizada);
    }
    
    /**
     * Agregar una lámina repetida.
     */
    @Transactional
    public LaminaResponse agregarRepetida(Long albumId, Integer numero) {
        Lamina lamina = laminaRepository.findByAlbumIdAndNumero(albumId, numero);
        if (lamina == null) {
            throw new RuntimeException("Lámina no encontrada con número " + numero + " en el álbum " + albumId);
        }
        
        // Incremento seguro ante posibles valores nulos en registros antiguos
        Integer actuales = lamina.getCantidadRepetidas();
        lamina.setCantidadRepetidas((actuales == null ? 0 : actuales) + 1);
        Lamina laminaActualizada = laminaRepository.save(lamina);
        return convertirAResponse(laminaActualizada);
    }
    
    /**
     * Eliminar una lámina.
     */
    @Transactional
    public void eliminar(Long id) {
        Lamina lamina = laminaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lámina no encontrada con ID: " + id));
        // En lugar de borrar físicamente, marcamos como no adquirida
        lamina.setAdquirida(false);
        laminaRepository.save(lamina);
    }
    
    /**
     * Obtener láminas faltantes de un álbum.
     */
    public List<LaminaResponse> obtenerFaltantes(Long albumId) {
        validarAlbumExiste(albumId);
        return laminaRepository.findByAlbumIdAndAdquiridaFalse(albumId).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener láminas repetidas de un álbum.
     */
    public List<LaminaRepetidaResponse> obtenerRepetidas(Long albumId) {
        validarAlbumExiste(albumId);
        return laminaRepository.findByAlbumIdAndCantidadRepetidasGreaterThan(albumId, 0).stream()
                .map(this::convertirARepetidaResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Validar que el álbum existe.
     */
    private void validarAlbumExiste(Long albumId) {
        if (!albumRepository.existsById(albumId)) {
            throw new RuntimeException("Álbum no encontrado con ID: " + albumId);
        }
    }
    
    /**
     * Convertir entidad Lamina a LaminaResponse.
     */
    private LaminaResponse convertirAResponse(Lamina lamina) {
        LaminaResponse response = new LaminaResponse();
        response.setId(lamina.getId());
        response.setNumero(lamina.getNumero());
        response.setNombre(lamina.getNombre());
        response.setImagen(lamina.getImagen());
        response.setAdquirida(lamina.getAdquirida());
        response.setCantidadRepetidas(lamina.getCantidadRepetidas());
        response.setAlbumId(lamina.getAlbum().getId());
        return response;
    }
    
    /**
     * Convertir entidad Lamina a LaminaRepetidaResponse.
     */
    private LaminaRepetidaResponse convertirARepetidaResponse(Lamina lamina) {
        LaminaRepetidaResponse response = new LaminaRepetidaResponse();
        response.setId(lamina.getId());
        response.setNumero(lamina.getNumero());
        response.setNombre(lamina.getNombre());
        response.setCantidadRepetidas(lamina.getCantidadRepetidas());
        return response;
    }
}
