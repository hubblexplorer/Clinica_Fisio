package com.example.test_login2.dto;

import com.example.test_login2.entity.Medico;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto
{
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    private Medico medico;
}