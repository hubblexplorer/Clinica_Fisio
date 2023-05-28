package com.example.test_login2.repository;

import com.example.test_login2.entity.Medico;
import com.example.test_login2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findByName(String name);

    Optional<Paciente> findById(Long id);

    Paciente findByMedico(Medico medico);

    List<Paciente> findAllByMedico(Medico medico);

    Paciente findByid(Long id);
}

