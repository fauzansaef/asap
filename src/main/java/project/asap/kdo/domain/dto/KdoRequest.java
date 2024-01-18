package project.asap.kdo.domain.dto;

import lombok.Data;

@Data
public class KdoRequest {
    private final String jenis;
    private final String nomorPlat;
    private final String tahun;
    private final String deskripsi;
    private final String photo;
}
