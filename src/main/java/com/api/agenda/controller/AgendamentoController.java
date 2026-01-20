package com.api.agenda.controller;

import com.api.agenda.dto.AgendamentoRequestDTO;
import com.api.agenda.dto.AgendamentoResponseDTO;
import com.api.agenda.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }


    // GET
    @GetMapping("/futuros")
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarAgendamentos(
            @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.ASC) Pageable paginacao
    ) {
        Page<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentosFuturos(paginacao);
        return ResponseEntity.ok(agendamentos);
    }

    // POST
    @PostMapping("/criar")
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO responseDTO = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // PATCH
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizarAgendamento(@PathVariable UUID id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO responseDTO = agendamentoService.atualizarParcial(id, dto);
        return ResponseEntity.ok(responseDTO);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletarAgendamento(@PathVariable UUID id) {
        agendamentoService.deletarAgendamento(id);
        return ResponseEntity.ok("Agendamento deletado com sucesso.");
    }
}
