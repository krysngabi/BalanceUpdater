package com.abovebytes.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Basic(optional = false)
    @Column(name = "product_name")
    private String productName;

    @Basic(optional = false)
    @Column(name = "product_cost")
    private Double cost;

    @Column(name = "status", length = 1)
    private int status = 1;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "product_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date productCreatedDate;

    @Basic()
    @Column(name = "product_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date productModifyDate;

    public Product(int productId) {
        this.productId = productId;
    }
}
