package com.example.gestion_laminas_albunes_api.response;
import java.util.List;
import com.example.gestion_laminas_albunes_api.model.Lamina;

import lombok.Data;

@Data
public class LaminasResponse {
    private int status;
    private String mensaje;
    private List<Lamina> datos;
}
