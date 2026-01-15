package com.api.agenda.controller;

import com.api.agenda.dto.ServicoDTO;
import com.api.agenda.entity.Servico;
import com.api.agenda.repository.ServicoRepository;
import com.api.agenda.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> cadastrar(@RequestBody @Valid ServicoDTO dados) {
        servicoService.salvarServico(dados);
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Servico>> listar() {
        var lista = servicoService.listarServicos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterPorId(@PathVariable UUID id) {
        var servico = servicoService.buscarServico(id);
        return ResponseEntity.ok(servico);
    }
}
