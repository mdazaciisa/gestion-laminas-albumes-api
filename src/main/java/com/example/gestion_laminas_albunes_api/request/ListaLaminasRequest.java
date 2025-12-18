package com.example.gestion_laminas_albunes_api.request;

import lombok.Data;
import java.util.List;

/**
 * DTO para agregar múltiples láminas a un álbum de forma masiva.
 */
@Data
public class ListaLaminasRequest {
    
    private List<Integer> numeros; // Lista de números de láminas a agregar
}
