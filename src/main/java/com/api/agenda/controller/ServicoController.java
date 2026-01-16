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

    @GetMapping("/{slug}")
    public ResponseEntity<Servico> obterPorSlug(@PathVariable String slug) {
        var servico = servicoService.buscarPorSlug(slug);
        return ResponseEntity.ok(servico);
    }


    // se slug nao funcionar pode ser por id
    @GetMapping("/id/{id}")
    public ResponseEntity<Servico> obterPorId(@PathVariable UUID id) {
        var servico = servicoService.buscarServico(id);
        return ResponseEntity.ok(servico);
    }


    @PatchMapping("/edit/{slug}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable String slug, @RequestBody ServicoDTO dadosAtualizados) {
        var servicoAtualizado = servicoService.atualizarServico(slug, dadosAtualizados);
        return ResponseEntity.ok(servicoAtualizado);
    }

    @DeleteMapping("/delete/{slug}")
    public ResponseEntity<String> deletarServico(@PathVariable String slug) {
        servicoService.deletarServico(slug);
        return ResponseEntity.ok("Serviço deletado com sucesso.");
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deletarServicoPorId(@PathVariable UUID id) {
        servicoService.deletarServico(id);
        return ResponseEntity.ok("Serviço deletado com sucesso.");
    }
}
