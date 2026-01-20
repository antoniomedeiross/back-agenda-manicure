package com.api.agenda.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AgendamentoRequestDTO(
        @NotNull UUID clienteId,
        @NotNull UUID manicureId,
        @NotEmpty List<UUID> servicosIds,
        @NotNull @Future LocalDateTime dataHora
) {
}
