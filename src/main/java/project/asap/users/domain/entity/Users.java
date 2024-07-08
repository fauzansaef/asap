package project.asap.users.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Users extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "section_id")
    private Long sectionId;
    @Column(name = "sub_section_id")
    private Long subSectionId;
    @Column(name = "name")
    private String name;
    @Column(name = "ip")
    private String ip;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "role")
    private Integer role;
    @JsonIgnore
    @Column(name = "device_token")
    private String deviceToken;
    @Column(name = "photo")
    private String photo;
    @JsonIgnore
    @Column(name = "remember_token")
    private String rememberToken;
    @Column(name = "unit_kerja")
    private String unitKerja;
    @Column(name = "jabatan")
    private String jabatan;
    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Sections sections;
    @ManyToOne
    @JoinColumn(name = "sub_section_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SubSections subSections;


}
