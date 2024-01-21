package project.asap.setting.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.users.domain.entity.Users;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "settings")
@Data
public class Settings extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "kasubag_id")
    private Long kasubagId;
    @Column(name = "slide1")
    private String slide1;
    @Column(name = "slide2")
    private String slide2;
    @Column(name = "slide3")
    private String slide3;
    @Column(name = "slide4")
    private String slide4;
    @Column(name = "slide5")
    private String slide5;
    @Column(name = "slide6")
    private String slide6;
    @Column(name = "text1")
    private String text1;
    @Column(name = "text2")
    private String text2;
    @Column(name = "text3")
    private String text3;
    @Column(name = "text4")
    private String text4;
    @Column(name = "text5")
    private String text5;
    @Column(name = "text6")
    private String text6;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kasubag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users users;

}
