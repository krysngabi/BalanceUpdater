package com.abovebytes.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_address")
    private String address;

    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_status")
    private int status;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "customer_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerCreateDate;

    @Basic()
    @Column(name = "customer_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerModifyDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "activity_fk", foreignKeyDefinition = "FOREIGN KEY (activity_id) REFERENCES tb_activity (activity_id)"))
    private Activity activity;

    public Customer(int customerId) {
        this.customerId = customerId;
    }
}
