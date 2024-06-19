package project.asap.kdo.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReqKdoRequest  {
    private Long idKdo;
    private String notaDinas;
    private String tujuan;
    private LocalDate tglPinjam;
    private LocalDate tglKembali;
    private String pemakai;
    private String namaPeminjam;
    private String requestNote;


}
