package com.example.test_login2.dto;

import com.example.test_login2.entity.Medico;
import com.example.test_login2.entity.Paciente;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDto
{
    private Long id;


    private Paciente paciente;


    private java.sql.Date date;

    private LocalTime Time;

}