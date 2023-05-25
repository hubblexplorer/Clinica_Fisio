package com.example.test_login2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Date;
import java.time.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int MedID;

    @Column(nullable=false)
    private String TypeName;

    @Column(nullable=false)
    private java.sql.Date date;

    @Column(nullable = false)
    private java.sql.Time Time;



    public void setId(Long id) {
        this.id = id;
    }

    public void setMedID(int medID) {
        MedID = medID;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }
}
