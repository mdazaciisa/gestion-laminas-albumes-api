package com.example.gestion_laminas_albunes_api.service;

import com.example.gestion_laminas_albunes_api.model.Album;
import com.example.gestion_laminas_albunes_api.model.Lamina;
import com.example.gestion_laminas_albunes_api.repository.AlbumRepository;
import com.example.gestion_laminas_albunes_api.repository.LaminaRepository;
import com.example.gestion_laminas_albunes_api.request.LaminaRequest;
import com.example.gestion_laminas_albunes_api.response.LaminaRepetidaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaminaService {

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    /* Obtener todas las láminas de un álbum */
    public List<Lamina> obtenerPorAlbum(Long albumId) {
        if (!validarAlbumExiste(albumId)) {
            return null;
        }
        return laminaRepository.findByAlbumId(albumId);
    }

    /* Obtener una lámina por ID */
    public Lamina obtenerPorId(Long id) {
        return laminaRepository.findById(id).orElse(null);
    }

    /* Crear una nueva lámina en un álbum */
    @Transactional
    public Lamina crear(Long albumId, LaminaRequest request) {
        Album album = albumRepository.findById(albumId).orElse(null);

        // Verificar si ya existe una lámina con ese número en el álbum
        Lamina laminaExistente = laminaRepository.findByAlbumIdAndNumero(albumId, request.getNumero());
        if (laminaExistente != null) {
            return null;
        }

        Lamina lamina = new Lamina();
        lamina.setNumero(request.getNumero());
        lamina.setNombre(request.getNombre());
        lamina.setImagen(request.getImagen());
        lamina.setAdquirida(request.getAdquirida() != null ? request.getAdquirida() : false);
        lamina.setCantidadRepetidas(request.getCantidadRepetidas() != null ? request.getCantidadRepetidas() : 0);
        lamina.setAlbum(album);

        return laminaRepository.save(lamina);
    }

    /* Crear múltiples láminas a partir de una lista de números */
    @Transactional
    public List<Lamina> crearMultiples(Long albumId, List<Integer> numeros) {
        Album album = albumRepository.findById(albumId).orElse(null);

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

        return laminasCreadas;
    }

    /* Actualizar una lámina existente */
    @Transactional
    public Lamina actualizar(Long id, LaminaRequest request) {
        Lamina lamina = laminaRepository.findById(id).orElse(null);

        if (lamina == null) {
            return null;
        }

        // Validar número duplicado dentro del mismo álbum
        if (request.getNumero() != null) {
            Lamina existente = laminaRepository.findByAlbumIdAndNumero(lamina.getAlbum().getId(), request.getNumero());
            if (existente != null && !existente.getId().equals(lamina.getId())) {
                return null;
            }
            lamina.setNumero(request.getNumero());
        }

        if (request.getNumero() != null)
            lamina.setNumero(request.getNumero());
        if (request.getNombre() != null)
            lamina.setNombre(request.getNombre());
        if (request.getImagen() != null)
            lamina.setImagen(request.getImagen());
        if (request.getAdquirida() != null)
            lamina.setAdquirida(request.getAdquirida());
        if (request.getCantidadRepetidas() != null)
            lamina.setCantidadRepetidas(request.getCantidadRepetidas());

        return laminaRepository.save(lamina);
    }

    /* Marcar una lámina como adquirida */
    @Transactional
    public Lamina marcarAdquirida(Long albumId, Integer numero) {
        Lamina lamina = laminaRepository.findByAlbumIdAndNumero(albumId, numero);
        if (lamina == null) {
            return null;
        }
        lamina.setAdquirida(true);
        return laminaRepository.save(lamina);
    }

    /* Agregar una lámina repetida */
    @Transactional
    public Lamina agregarRepetida(Long albumId, Integer numero) {
        Lamina lamina = laminaRepository.findByAlbumIdAndNumero(albumId, numero);
        if (lamina == null) {
            throw new RuntimeException("Lámina no encontrada con número " + numero + " en el álbum " + albumId);
        }
        // Incremento seguro ante posibles valores nulos en registros antiguos
        Integer actuales = lamina.getCantidadRepetidas();
        lamina.setCantidadRepetidas((actuales == null ? 0 : actuales) + 1);
        return laminaRepository.save(lamina);
    }

    /* Eliminar una lámina */
    @Transactional
    public Lamina eliminar(Long id) {
        Lamina lamina = laminaRepository.findById(id).orElse(null);
        if (lamina == null) {
            return null;
        }
        // En lugar de borrar físicamente, marcamos como no adquirida
        lamina.setAdquirida(false);
        return laminaRepository.save(lamina);
    }

    /* Obtener láminas faltantes de un álbum */
    public List<Lamina> obtenerFaltantes(Long albumId) {
        if (!validarAlbumExiste(albumId)) {
            return null;
        }
        return laminaRepository.findByAlbumIdAndAdquiridaFalse(albumId);
    }

    /* Obtener láminas repetidas de un álbum */
    public List<LaminaRepetidaResponse> obtenerRepetidas(Long albumId) {
        if (!validarAlbumExiste(albumId)) {
            return null;
        }
        return laminaRepository.findByAlbumIdAndCantidadRepetidasGreaterThan(albumId, 0).stream()
                .map(this::convertirARepetidaResponse)
                .collect(Collectors.toList());
    }

    /* Validar que el álbum existe */
    private boolean validarAlbumExiste(Long albumId) {
        return albumRepository.existsById(albumId);
    }

    /* Convertir entidad Lamina a LaminaRepetidaResponse */
    private LaminaRepetidaResponse convertirARepetidaResponse(Lamina lamina) {
        LaminaRepetidaResponse response = new LaminaRepetidaResponse();
        response.setId(lamina.getId());
        response.setNumero(lamina.getNumero());
        response.setNombre(lamina.getNombre());
        response.setCantidadRepetidas(lamina.getCantidadRepetidas());
        return response;
    }
}
