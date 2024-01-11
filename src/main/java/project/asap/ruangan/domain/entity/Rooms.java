package project.asap.ruangan.domain.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@SQLDelete(sql = "UPDATE rooms SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Rooms extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "kode")
    private String kode;
    @Column(name = "nama_ruangan")
    private String namaRuangan;
    @Column(name = "deskripsi")
    private String deskripsi;
    @Column(name = "photo")
    private String photo;
    @Column(name = "ready")
    private Integer ready;
    @Column(name = "deleted_at")
    private String deletedAt;

}
