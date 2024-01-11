package project.asap.ruangan.domain.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private String kode;
    private String namaRuangan;
    private String deskripsi;
    private String photo;
}
