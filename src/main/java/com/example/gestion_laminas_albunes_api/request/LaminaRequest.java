package com.example.gestion_laminas_albunes_api.request;

import lombok.Data;

/**
 * DTO para la creación o actualización de una lámina individual.
 */
@Data
public class LaminaRequest {
    
    private Integer numero;
    private String nombre;
    private String imagen;
    private Boolean adquirida;
    private Integer cantidadRepetidas;
}
