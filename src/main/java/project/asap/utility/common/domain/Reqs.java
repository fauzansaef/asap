package project.asap.utility.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.asap.users.domain.entity.Users;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "req_kdos")
@SQLDelete(sql = "UPDATE reqs SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Reqs extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "no_request", unique = true)
    private String noRequest;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "approve_id")
    private Long approveId;
    @Column(name = "progres_id")
    private Long progresId;
    @Column(name = "completed_id")
    private Long completedId;
    @Column(name = "approve_at")
    private LocalDateTime approveAt;
    @Column(name = "progres_at")
    private LocalDateTime progresAt;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    @Column(name = "request_note")
    private String requestNote;
    @Column(name = "approve_note")
    private String approveNote;
    @Column(name = "progres_note")
    private String progresNote;
    @Column(name = "completed_note")
    private String completedNote;
    @Column(name = "start")
    private Integer start;
    @Column(name = "comment")
    private String comment;
    @Column(name = "comment_at")
    private LocalDateTime commentAt;
    @Column(name = "type")
    private Integer type;
    @Column(name = "status_req")
    private Integer statusReq;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Users user_id;
    @JsonIgnore
    @JoinColumn(name = "approve_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Users approve_id;
    @JsonIgnore
    @JoinColumn(name = "progres_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Users progres_id;
    @JsonIgnore
    @JoinColumn(name = "completed_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Users completed_id;

}
