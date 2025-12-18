package com.example.gestion_laminas_albunes_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un álbum de colección.
 * Contiene información sobre el álbum y las láminas que lo componen.
 */
@Entity
@Table(name = "albumes")
@Data
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nombre;
    
    @Column(length = 500)
    private String imagen; // URL o ruta de la imagen del álbum
    
    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;
    
    @Column(name = "tipo_laminas", length = 100)
    private String tipoLaminas; // Ejemplo: "deportes", "animales", "entretenimiento"
    
    @Column(name = "total_laminas")
    private Integer totalLaminas; // Número total de láminas del álbum
    
    @Column(length = 1000)
    private String descripcion;
    
    // Relación uno a muchos con Lamina
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lamina> laminas = new ArrayList<>();
}
