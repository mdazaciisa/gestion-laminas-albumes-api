package com.example.gestion_laminas_albunes_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
    name = "laminas",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"album_id", "numero"})
    }
)
@Data
public class Lamina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer numero; // Número de la lámina en el álbum
    
    @Column(length = 200)
    private String nombre;
    
    @Column(length = 500)
    private String imagen; // URL o ruta de la foto de la lámina (opcional)
    
    @Column(nullable = false, name = "adquirida")
    private Boolean adquirida = false; // Indica si la lámina ha sido adquirida
    
    @Column(name = "cantidad_repetidas", nullable = false)
    private Integer cantidadRepetidas = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
}
