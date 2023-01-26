package com.abovebytes.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_solde")
public class Solde implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solde_id")
    private int soldeId;

    @Basic(optional = false)
    @Column(name = "solde_value")
    private Double soldeValue = 0.0;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "solde_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date soldeCreateDate;

    @Basic()
    @Column(name = "solde_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date soldeModifyDate;
}
