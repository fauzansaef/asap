package project.asap.arsip.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "req_arsip_details")
@Data
public class ReqArsipDetails extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "req_arsip_id")
    private Long reqArsipId;
    @Column(name = "arsip_id")
    private Long arsipId;
    @Column(name = "jumlah")
    private Integer jumlah;
    @Column(name = "jumlah_dikembalikan")
    private Integer jumlahDikembalikan;
    @Column(name = "catatan_pelaksana")
    private String catatanPelaksana;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @JsonIgnore
    @JoinColumn(name = "req_arsip_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private ReqArsip reqArsip;
    @JsonIgnore
    @JoinColumn(name = "arsip_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Arsip arsip;

}
