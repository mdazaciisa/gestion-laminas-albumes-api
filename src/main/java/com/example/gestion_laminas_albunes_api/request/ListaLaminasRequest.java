package com.example.gestion_laminas_albunes_api.request;

import lombok.Data;
import java.util.List;

@Data
public class ListaLaminasRequest {
    
    private List<Integer> numeros; // Lista de números de láminas a agregar
}
