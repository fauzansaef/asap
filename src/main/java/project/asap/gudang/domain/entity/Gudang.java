package project.asap.gudang.domain.entity;

import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "gudang")
@Data
public class Gudang extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "code")
    private String code;
}
