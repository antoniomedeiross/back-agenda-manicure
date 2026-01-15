package com.api.agenda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientResponseDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email) {
}
