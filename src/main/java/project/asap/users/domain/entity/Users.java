package project.asap.users.domain.entity;

import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
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
    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "role")
    private Integer role;
    @Column(name = "device_token")
    private String deviceToken;
    @Column(name = "photo")
    private String photo;
    @Column(name = "remember_token")
    private String rememberToken;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
