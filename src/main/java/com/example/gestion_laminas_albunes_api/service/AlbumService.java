package com.example.gestion_laminas_albunes_api.service;

import com.example.gestion_laminas_albunes_api.model.Album;
import com.example.gestion_laminas_albunes_api.model.Lamina;
import com.example.gestion_laminas_albunes_api.repository.AlbumRepository;
import com.example.gestion_laminas_albunes_api.repository.LaminaRepository;
import com.example.gestion_laminas_albunes_api.request.AlbumRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private LaminaRepository laminaRepository;

    /* Obtener todos los álbumes */
    public List<Album> obtenerTodos() {
        return albumRepository.findAll();
    }

    /* Obtener un álbum por ID */
    public Album obtenerId(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    /* Crear un nuevo álbum */
    @Transactional
    public Album crear(AlbumRequest request) {
        if(request.getNombre() == null) {
            return null;
        }
        if(request.getTotalLaminas() == null) {
            return null;
        }
        
        Album album = new Album();
        album.setNombre(request.getNombre());
        album.setImagen(request.getImagen());
        album.setFechaLanzamiento(request.getFechaLanzamiento());
        album.setTipoLaminas(request.getTipoLaminas());
        album.setTotalLaminas(request.getTotalLaminas());
        album.setDescripcion(request.getDescripcion());

        Album albumGuardado = albumRepository.save(album);
        // Nota: La creación masiva de láminas se realiza mediante el endpoint
        // específico
        // POST /api/laminas/album/{albumId}/multiple
        return albumRepository.save(albumGuardado);
    }

    /* Actualizar un álbum existente */
    @Transactional
    public Album actualizar(Long id, AlbumRequest request) {
        Album album = albumRepository.findById(id).orElse(null);

        if (album == null) {
            return null;
        }

        album.setNombre(request.getNombre());
        album.setImagen(request.getImagen());
        album.setFechaLanzamiento(request.getFechaLanzamiento());
        album.setTipoLaminas(request.getTipoLaminas());
        album.setTotalLaminas(request.getTotalLaminas());
        album.setDescripcion(request.getDescripcion());

        return albumRepository.save(album);
    }

    /* Eliminar un álbum */
    @Transactional
    public void eliminar(Long id) {
        Album album = albumRepository.findById(id).orElse(null);
        albumRepository.delete(album);
    }

    /* Buscar álbumes por nombre */
    public List<Album> buscarPorNombre(String nombre) {
        return albumRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .collect(Collectors.toList());
    }

    /* Album con estadísticas actualizadas */
    public Album estadisticasAlbum(Album album) {
        Album response = new Album();
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
        response.setLaminasFaltantes(album.getTotalLaminas() - (int) adquiridas);

        if (laminas.size() > 0) {
            double porcentaje = (adquiridas * 100.0) / album.getTotalLaminas();
            response.setPorcentajeCompletado(Math.round(porcentaje * 100.0) / 100.0);
        } else {
            response.setPorcentajeCompletado(0.0);
        }
        return response;
    }
}
