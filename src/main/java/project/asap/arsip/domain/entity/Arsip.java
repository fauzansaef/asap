package project.asap.arsip.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.gudang.domain.entity.Gudang;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "arsip")
@SQLDelete(sql = "UPDATE arsip SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Arsip extends AuditEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
        @Column(name = "id_tipe_arsip")
        private Long idTipeArsip;
        @Column(name = "kode")
        private String kode;
        @Column(name = "nama")
        private String nama;
        @Column(name = "tahun")
        private String tahun;
        @Column(name = "deskripsi")
        private String deskripsi;
        @Column(name = "jumlah")
        private String jumlah;
        @Column(name = "foto")
        private String foto;
        @Column(name = "deleted_at")
        private String deletedAt;

    @JsonIgnore
    @JoinColumn(name = "id_gudang",referencedColumnName = "id",insertable = false, updatable = false)
    @ManyToOne
    private Gudang gudang;

}
