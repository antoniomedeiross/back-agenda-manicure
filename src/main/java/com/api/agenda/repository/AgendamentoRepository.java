package com.api.agenda.repository;

import com.api.agenda.entity.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    Page<Agendamento> findAgendamentoByDataHoraAfter(LocalDateTime dataHoraAfter, Pageable pageable);

    List<Agendamento> findByManicure_EmailAndDataHoraBetween(String manicureEmail, LocalDateTime start, LocalDateTime end);
}
