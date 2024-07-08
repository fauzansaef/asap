package project.asap.box.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.rak.domain.entity.Rak;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "box")
@Data
public class Box extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "code")
    private String code;
    @Column(name = "id_rak")
    private Long idRak;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_rak", insertable = false, updatable = false)
    private Rak rak;
}
