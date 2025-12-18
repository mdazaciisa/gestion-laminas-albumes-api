package com.example.gestion_laminas_albunes_api.response;

import lombok.Data;

/**
 * DTO para la respuesta de una lámina.
 */
@Data
public class LaminaResponse {
    
    private Long id;
    private Integer numero;
    private String nombre;
    private String imagen;
    private Boolean adquirida;
    private Integer cantidadRepetidas;
    private Long albumId;
}
