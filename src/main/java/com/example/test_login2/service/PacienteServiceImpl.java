package com.example.test_login2.service;


import com.example.test_login2.dto.PacienteDto;
import com.example.test_login2.entity.Paciente;
import com.example.test_login2.repository.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    private PacienteRepository pacienteRepository;


    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void savePaciente(PacienteDto pacienteDto) {
        Paciente paciente = new Paciente();
        paciente.setName(pacienteDto.getName());
        paciente.setMedico(pacienteDto.getMedico());
        pacienteRepository.save(paciente);

    }


    @Override
    public Paciente findPacienteByName(String name) {
        return pacienteRepository.findByName(name);
    }

    @Override
    public List<PacienteDto> findAllUsers() {
        List<Paciente> paciente = pacienteRepository.findAll();
        return paciente.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private PacienteDto mapToUserDto(Paciente paciente){
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setName(paciente.getName());

        return pacienteDto;
    }

}