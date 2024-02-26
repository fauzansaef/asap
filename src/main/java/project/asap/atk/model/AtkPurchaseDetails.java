package project.asap.atk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project.asap.utility.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "atk_purchase_details")
@Data
public class AtkPurchaseDetails extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "atk_purchase_id")
    private Integer atkPurchaseId;
    @Column(name = "atk_id")
    private Integer atkId;
    @Column(name = "jumlah")
    private String jumlah;
    @Column(name = "harga")
    private String harga;
    @Column(name = "sub_total")
    private String subTotal;
    @ManyToOne
    @JoinColumn(name = "atk_id", insertable = false, updatable = false)
    private Atks atk;

    @JsonIgnore
    @JoinColumn(name = "atk_purchase_id",referencedColumnName = "id",insertable = false, updatable = false)
    @ManyToOne
    private AtkPurchases atkPurchases;

}
