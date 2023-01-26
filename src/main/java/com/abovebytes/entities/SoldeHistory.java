package com.abovebytes.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_solde_history")
public class SoldeHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;

    @Basic(optional = false)
    @Column(name = "from_cost")
    private Double fromCost;

    @Basic(optional = false)
    @Column(name = "entries")
    private Double entries;

    @Basic(optional = false)
    @Column(name = "expenses_cost")
    private Double expensesCost;

    @Basic(optional = false)
    @Column(name = "to_cost")
    private Double toCost;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "history_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date historyCreatedDate;

    @Basic()
    @Column(name = "history_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date historyModifyDate;
}
