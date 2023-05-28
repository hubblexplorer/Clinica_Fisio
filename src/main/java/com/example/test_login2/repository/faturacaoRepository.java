package com.example.test_login2.repository;

import com.example.test_login2.entity.Agenda;
import com.example.test_login2.entity.faturacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  faturacaoRepository extends JpaRepository<faturacao, Long> {
    List<faturacao> findAllBy();
}
