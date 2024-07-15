package project.asap.ruangan.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RoomRequest {
    @NotNull
    @NotEmpty
    private String kode;
    @NotNull
    @NotEmpty
    private String namaRuangan;
    @NotNull
    @NotEmpty
    private String deskripsi;
    private String photo;
    private String lokasi;
    private String kapasitas;
    private Long idUser;
}
