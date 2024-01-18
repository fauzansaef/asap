package project.asap.bmn.domain.dto;

import lombok.Data;

@Data
public class BmnRequest {
    private String kode;
    private String namaBmn;
    private String tahun;
    private String deskripsi;
    private Integer stock;
    private String photo;
}
