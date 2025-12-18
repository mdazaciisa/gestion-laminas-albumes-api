package com.example.gestion_laminas_albunes_api.response;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO para la respuesta de un álbum.
 */
@Data
public class AlbumResponse {
    
    private Long id;
    private String nombre;
    private String imagen;
    private LocalDate fechaLanzamiento;
    private String tipoLaminas;
    private Integer totalLaminas;
    private String descripcion;
    private Integer laminasAdquiridas; // Cantidad de láminas adquiridas
    private Integer laminasFaltantes; // Cantidad de láminas faltantes
    private Double porcentajeCompletado; // Porcentaje de completación del álbum
}
