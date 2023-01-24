package com.abovebytes.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class TotalSum implements Serializable {
    @Id
    @Column(name = "tid")
    private int paymentId;
    @Column
    private Double cost = 0.0;
    @Column
    private Double discount = 0.0;
    @Column
    private Double total = 0.0;

    @Column
    private int quantity = 0;

    public TotalSum(Double cost, Double total) {
        this.cost = cost;
        this.total = total;
    }
}
