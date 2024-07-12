package project.asap.arsip.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.utility.common.AuditEntity;
import project.asap.utility.common.domain.Reqs;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "req_arsip")
@Data
public class ReqArsip extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nota_dinas")
    private String notaDinas;
    @Column(name = "req_id")
    private Long reqId;
    @Column(name = "tujuan")
    private String tujuan;
    @Column(name = "tgl_pinjam")
    private LocalDate tglPinjam;
    @Column(name = "tgl_kembali")
    private LocalDate tglKembali;
    @Column(name = "penerima")
    private String penerima;
    @Column(name = "tgl_kembali_real")
    private LocalDate tglKembaliReal;
    @Column(name = "status")
    private Integer status;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @JsonIgnore
    @JoinColumn(name = "req_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Reqs reqs;

}
