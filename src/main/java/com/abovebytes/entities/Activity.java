package com.abovebytes.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_activity")
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private int activityId;

    @Basic(optional = false)
    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Basic(optional = false)
    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "activity_status", length = 1)
    private int activityStatus = 1;

    @Column(name = "user_create_id")
    private int userCreateId;

    @Column(name = "user_modify_id")
    private int userModifyId;

    @Basic()
    @Column(name = "activity_modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activityModifyDate;

    @Basic()
    @Column(name = "activity_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activityCreateDate;



    public Activity(int activityId) {
        this.activityId = activityId;
    }
}
