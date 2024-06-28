package project.asap.utility.common.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "no_reqs")
@Data
public class NoReqs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tanggal")
    private String tanggal;
    @Column(name = "type")
    private Integer type;
    @Column(name = "no_urut")
    private Integer noUrut;


}
