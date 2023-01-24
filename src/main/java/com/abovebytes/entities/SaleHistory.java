package com.abovebytes.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_sales_history")
public class SaleHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_history_id")
    private int saleHistoryId;

    @Basic(optional = false)
    @Column(name = "paid")
    private Double paid = 0.0;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Basic()
    @Column(name = "history_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleHistoryCreatedDate;
}
