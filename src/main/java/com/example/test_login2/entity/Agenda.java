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
@Table(name="Agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Pacientes_id", referencedColumnName = "id")
    private Paciente paciente;


    @Column(nullable=false)
    private java.sql.Date date;

    @Column(nullable = false)
    private java.sql.Time Time;



    public void setId(Long id) {
        this.id = id;
    }

    public void setPacID(Long PacID) {
        PacID = PacID;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }
}
