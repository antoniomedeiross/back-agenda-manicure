package com.api.agenda.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull @Min(15) Integer duracao,
        @NotNull @Positive BigDecimal preco
) {}