package com.example.test_login2.service;

import com.example.test_login2.dto.PacienteDto;
import com.example.test_login2.entity.Paciente;

import java.util.List;

public interface PacienteService {
    void savePaciente(PacienteDto pacienteDto);

    Paciente findPacienteByName(String name);

    List<PacienteDto> findAllUsers();
}
