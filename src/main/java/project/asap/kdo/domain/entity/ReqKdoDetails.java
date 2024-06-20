package project.asap.kdo.domain.entity;

import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "req_kdo_details")
@Data
public class ReqKdoDetails extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "req_kdo_id")
    private Long reqKdoId;
    @Column(name = "kdo_id")
    private Long kdoId;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JoinColumn(name = "req_kdo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private ReqKdos reqKdos;

    @JoinColumn(name = "kdo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Kdos kdos;

}
