package com.api.agenda.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public record ManicureResponseDTO(String name, String email, String especialidade) {

    public ManicureResponseDTO(com.api.agenda.entity.Manicure manicure) {
        this(manicure.getName(), manicure.getEmail(), manicure.getEspecialidade());
    }
}
