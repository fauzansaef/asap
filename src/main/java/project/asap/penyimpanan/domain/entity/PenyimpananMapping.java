package project.asap.penyimpanan.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.arsip.domain.entity.Arsip;
import project.asap.atk.model.Atks;
import project.asap.bmn.domain.entity.Bmns;
import project.asap.box.domain.entity.Box;
import project.asap.gudang.domain.entity.Gudang;
import project.asap.lemari.domain.entity.Lemari;
import project.asap.rak.domain.entity.Rak;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "penyimpanan_mapping")
@Data
public class PenyimpananMapping extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_bmn")
    private Long idBmn;
    @Column(name = "id_atk")
    private Long idAtk;
    @Column(name = "id_arsip")
    private Long idArsip;
    @Column(name = "id_gudang")
    private Long idGudang;
    @Column(name = "id_lemari")
    private Long idLemari;
    @Column(name = "id_rak")
    private Long idRak;
    @Column(name = "id_box")
    private Long idBox;
    @Column(name = "kode_batch")
    private String kodeBatch;
    @JsonIgnore
    @JoinColumn(name = "id_bmn", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    Bmns bmns;
    @JsonIgnore
    @JoinColumn(name = "id_atk", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    Atks atks;
    @JsonIgnore
    @JoinColumn(name = "id_arsip", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    Arsip arsip;

    @JoinColumn(name = "id_gudang", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Gudang gudang;

    @JoinColumn(name = "id_lemari", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Lemari lemari;

    @JoinColumn(name = "id_rak", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Rak rak;

    @JoinColumn(name = "id_box", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Box box;


}
