package project.asap.atk.model;

import lombok.Data;
import project.asap.utility.common.AuditEntity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atk_opnames")
@Data
public class AtkOpnames extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "atk_id")
    private Integer atkId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "jumlah")
    private Integer jumlah;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
