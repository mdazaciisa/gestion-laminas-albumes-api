package com.example.gestion_laminas_albunes_api.response;

import lombok.Data;

/**
 * DTO para la respuesta de láminas repetidas.
 * Incluye el número de la lámina y la cantidad de repetidas.
 */
@Data
public class LaminaRepetidaResponse {
    
    private Long id;
    private Integer numero;
    private String nombre;
    private Integer cantidadRepetidas;
}
