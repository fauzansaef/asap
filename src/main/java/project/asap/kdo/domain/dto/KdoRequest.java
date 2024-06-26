package project.asap.kdo.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class KdoRequest {
    @NotNull
    @NotEmpty
    private final String jenis;
    @NotNull
    @NotEmpty
    private final String nomorPlat;
    @NotNull
    @NotEmpty
    private final String tahun;
    @NotNull
    @NotEmpty
    private final String deskripsi;
    @NotNull
    @NotEmpty
    private final String photo;
}
