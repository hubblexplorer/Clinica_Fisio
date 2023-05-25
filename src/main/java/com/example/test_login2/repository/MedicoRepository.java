package com.example.test_login2.repository;

import com.example.test_login2.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    List<Medico> findByName(String name);

    Optional<Medico> findById(Long id);

    Medico findByFuncionario_Id(Long funcionarioId);

}

