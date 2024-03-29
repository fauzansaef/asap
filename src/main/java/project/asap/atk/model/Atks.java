package project.asap.atk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.utility.common.AuditEntity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atks")
@Data
public class Atks extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "kode")
    private String kode;
    @Column(name = "nama_atk")
    private String namaAtk;
    @Column(name = "deskripsi")
    private String deskripsi;
    @Column(name = "photo")
    private String photo;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "harga")
    private String harga;
    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
