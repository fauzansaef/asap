package project.asap.users.domain.entity;

import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "sections")
@Data
public class Sections extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "kode")
    private String kode;
    @Column(name = "section_name")
    private String sectionName;
}
