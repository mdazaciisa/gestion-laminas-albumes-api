package com.example.gestion_laminas_albunes_api.response;
import lombok.Data;
import java.util.List;
import com.example.gestion_laminas_albunes_api.model.Album;

@Data
public class AlbumesResponse {
    private int status;
    private String mensaje;
    private List<Album> datos;
}
