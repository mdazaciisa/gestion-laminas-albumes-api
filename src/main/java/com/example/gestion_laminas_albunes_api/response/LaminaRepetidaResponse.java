package com.example.gestion_laminas_albunes_api.response;

import lombok.Data;

@Data
public class LaminaRepetidaResponse {
    
    private Long id;
    private Integer numero;
    private String nombre;
    private Integer cantidadRepetidas;
}
