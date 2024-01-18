package project.asap.kdo.domain.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kdos")
@SQLDelete(sql = "UPDATE kdos SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Kdos extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "jenis")
    private String jenis;
    @Column(name = "nomor_plat")
    private String nomorPlat;
    @Column(name = "tahun")
    private String tahun;
    @Column(name = "deskripsi")
    private String deskripsi;
    @Column(name = "photo")
    private String photo;
    @Column(name = "ready")
    private Integer ready;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
