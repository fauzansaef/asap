package project.asap.bmn.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BmnRequest {
    @NotNull
    @NotEmpty
    private String kode;
    @NotNull
    @NotEmpty
    private String namaBmn;
    private String tahun;
    @NotNull
    @NotEmpty
    private String deskripsi;
    @NotNull
    @NotEmpty
    private Integer stock;
    private String photo;
    private String status;
    private String namaPenanggungJawab;
    private String tahunPengadaan;
    private Long idUser;
}
