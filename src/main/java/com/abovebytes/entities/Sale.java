package com.abovebytes.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private int saleId;

    @Basic(optional = false)
    @Column(name = "sale_paid")
    private Double paid = 0.0;

    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "status", length = 1)
    private int status = 1;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "sale_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleCreatedDate;

    @Basic()
    @Column(name = "sale_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleModifyDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "product_fk", foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES tb_products (product_id)"))
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "customer_fk", foreignKeyDefinition = "FOREIGN KEY (customer_id) REFERENCES tb_customers (customer_id)"))
    private Customer customer;
}
