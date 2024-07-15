package project.asap.atk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.utility.common.AuditEntity;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "atks")
@SQLDelete(sql = "UPDATE atks SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Atks extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotEmpty
    @NotNull
    @Column(name = "kode")
    private String kode;
    @NotEmpty
    @NotNull
    @Column(name = "nama_atk")
    private String namaAtk;
    @Column(name = "deskripsi")
    private String deskripsi;
    @Column(name = "photo")
    private String photo;
    @NotEmpty
    @NotNull
    @Column(name = "stock")
    private Integer stock;
    @NotEmpty
    @NotNull
    @Column(name = "harga")
    private String harga;
    @Column(name = "kode_lokasi")
    private String kodeLokasi;
    @Column(name = "tahun")
    private String tahun;
    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
