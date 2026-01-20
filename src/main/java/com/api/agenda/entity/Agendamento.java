package com.api.agenda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne // Muitos agendamentos para um cliente
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne // Muitos agendamentos para uma manicure
    @JoinColumn(name = "manicure_id", nullable = false)
    private Manicure manicure;

    @ManyToMany // Muitos agendamentos podem ter muitos serviços
    @JoinTable(
            name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos;

    private LocalDateTime dataHora;

    private String status; // "AGENDADO", "CANCELADO", "CONCLUIDO"
}
