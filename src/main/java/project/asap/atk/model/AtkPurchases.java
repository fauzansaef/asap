package project.asap.atk.model;

import lombok.Data;
import project.asap.utility.common.AuditEntity;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "atk_purchases")
@Data
public class AtkPurchases extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "nomor_po")
    private String nomorPo;
    @Column(name = "tgl_pembelian")
    private LocalDate tglPembelian;
    @Column(name = "total")
    private String total;
    @Column(name = "status")
    private Integer status;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "atkPurchases")
    List<AtkPurchaseDetails> atkPurchaseDetails;
}
