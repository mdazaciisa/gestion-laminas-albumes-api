package com.example.gestion_laminas_albunes_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Integer totalLaminas;
    
    @Column(length = 1000)
    private String descripcion;
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Lamina> laminas = new ArrayList<>();

    // Estadísticas
    private Integer laminasAdquiridas; // Cantidad de láminas adquiridas
    private Integer laminasFaltantes; // Cantidad de láminas faltantes
    private Double porcentajeCompletado; // Porcentaje de completación del álbum

}
