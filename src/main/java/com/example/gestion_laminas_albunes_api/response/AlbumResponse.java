package com.example.gestion_laminas_albunes_api.response;
import com.example.gestion_laminas_albunes_api.model.Album;
import lombok.Data;

@Data
public class AlbumResponse {
    private int status;
    private String mensaje;
    private Album datos;
}
