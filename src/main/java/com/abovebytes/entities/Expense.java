package com.abovebytes.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_expenses")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @Basic(optional = false)
    @Column(name = "expense_name")
    private String expenseName;

    @Basic(optional = false)
    @Column(name = "expense_cost")
    private Double cost;

    @Column(name = "status", length = 1)
    private int status = 1;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "expense_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expenseCreatedDate;

    @Basic()
    @Column(name = "expense_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expenseModifyDate;

}
