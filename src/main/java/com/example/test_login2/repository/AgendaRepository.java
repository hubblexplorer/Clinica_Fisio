package com.example.test_login2.repository;

import com.example.test_login2.entity.Agenda;
import com.example.test_login2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByDate(java.sql.Date Date);
    List<Agenda> findAgendaByDateBetween(java.sql.Date startDate, java.sql.Date endDate);

    List<Agenda> findAllByPaciente(Paciente paciente);
}
