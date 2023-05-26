package com.example.test_login2.repository;


import com.example.test_login2.entity.Rececionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RececionistaRepository extends JpaRepository<Rececionista, Long> {

    List<Rececionista> findByName(String name);

    Optional<Rececionista> findById(Long id);

    Rececionista findByFuncionario_Id(Long funcionarioId);

}

