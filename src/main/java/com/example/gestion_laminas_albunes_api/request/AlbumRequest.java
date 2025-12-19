package com.example.gestion_laminas_albunes_api.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AlbumRequest {
    
    private String nombre;
    private String imagen;
    private LocalDate fechaLanzamiento;
    private String tipoLaminas;
    private Integer totalLaminas;
    private String descripcion;
}
