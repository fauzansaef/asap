package project.asap.kdo.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReqKdoRequest {
    @NotNull
    private Long idKdo;
    private String notaDinas;
    private String tujuan;
    @NotNull
    private LocalDate tglPinjam;
    @NotNull
    private LocalDate tglKembali;
    private String pemakai;
    @NotNull
    @NotEmpty
    private String namaPeminjam;
    private String requestNote;


}
