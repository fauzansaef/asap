package project.asap.kdo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.utility.common.AuditEntity;
import project.asap.utility.common.Reqs;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "req_kdos")
@SQLDelete(sql = "UPDATE req_kdos SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class ReqKdos extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "req_id")
    private Long reqId;
    @Column(name = "nota_dinas")
    private String notaDinas;
    @Column(name = "tujuan")
    private String tujuan;
    @Column(name = "tgl_pinjam")
    private LocalDate tglPinjam;
    @Column(name = "tgl_kembali")
    private LocalDate tglKembali;
    @Column(name = "pemakai")
    private String pemakai;
    @Column(name = "foto_terakhir")
    private String fotoTerakhir;
    @Column(name = "catatan_parkir")
    private String catatanParkir;
    @Column(name = "catatan_kembali")
    private String catatanKembali;
    @Column(name = "penerima")
    private String penerima;
    @Column(name = "diperbarui_pada")
    private LocalDateTime diperbaruiPada;
    @Column(name = "tgl_kembali_real")
    private LocalDate tglKembaliReal;
    @Column(name = "status")
    private Integer status;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "nama_peminjam")
    private String namaPeminjam;
    @JsonIgnore
    @JoinColumn(name = "request_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Reqs reqs;
}
