package com.example.gestion_laminas_albunes_api.repository;

import com.example.gestion_laminas_albunes_api.model.Lamina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaminaRepository extends JpaRepository<Lamina, Long> {
    
    // Buscar todas las láminas de un álbum específico
    List<Lamina> findByAlbumId(Long albumId);
    
    // Buscar láminas faltantes de un álbum (no adquiridas)
    List<Lamina> findByAlbumIdAndAdquiridaFalse(Long albumId);
    
    // Buscar láminas repetidas de un álbum (cantidad > 0)
    List<Lamina> findByAlbumIdAndCantidadRepetidasGreaterThan(Long albumId, Integer cantidad);
    
    // Buscar una lámina específica por álbum y número
    Lamina findByAlbumIdAndNumero(Long albumId, Integer numero);
}
