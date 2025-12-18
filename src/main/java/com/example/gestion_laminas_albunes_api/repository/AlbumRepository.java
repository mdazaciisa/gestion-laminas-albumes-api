package com.example.gestion_laminas_albunes_api.repository;

import com.example.gestion_laminas_albunes_api.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    
    // Buscar álbumes por nombre (búsqueda parcial)
    List<Album> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar álbumes por tipo de láminas
    List<Album> findByTipoLaminas(String tipoLaminas);
}
