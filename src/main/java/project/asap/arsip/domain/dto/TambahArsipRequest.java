package project.asap.arsip.domain.dto;

import lombok.Data;

@Data
public class TambahArsipRequest {
    private Long idTipeArsip;
    private String kode;
    private String nama;
    private String tahun;
    private String deskripsi;
    private Integer jumlahLembar;
    private String file;
    private Long idGudang;
    private Long idLemari;
    private Long idRak;
    private Long idBox;


}
