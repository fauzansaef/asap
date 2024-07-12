package project.asap.arsip.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReqArsipRequest {
    @NotNull
    private Long idArsip;
    private String notaDinas;
    private String tujuan;
    @NotNull
    private LocalDate tglPinjam;
    @NotNull
    private LocalDate tglKembali;
    private String requestNote;

}
