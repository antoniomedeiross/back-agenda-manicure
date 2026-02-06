package com.api.agenda.dto;

import java.time.LocalTime;

public record SlotDTO(LocalTime hora, boolean disponivel) {
}
