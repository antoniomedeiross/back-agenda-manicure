package com.api.agenda.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoResponseDTO(
        String clienteNome,
        String manicureNome,
        List<String> servicosNomes,
        LocalDateTime dataHora,
        String status
) {
}
