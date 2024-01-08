package project.asap.users.domain.entity;

import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "sub_sections")
@Data
public class SubSections extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "section_id")
    private Long sectionId;
    @Column(name = "kode")
    private String kode;
    @Column(name = "sub_section_name")
    private String subSectionName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Sections sections;

}
