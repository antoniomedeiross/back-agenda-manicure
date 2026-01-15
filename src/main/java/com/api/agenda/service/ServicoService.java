package com.api.agenda.service;

import com.api.agenda.dto.ServicoDTO;
import com.api.agenda.entity.Servico;
import com.api.agenda.repository.ServicoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void salvarServico(ServicoDTO dados) {
        var servico = new Servico();
        BeanUtils.copyProperties(dados, servico); // Atalho para copiar do DTO para Entity
        // Gera o slug a partir do nome
        String slugGerado = gerarSlug(servico.getNome());
        servico.setSlug(slugGerado);

        servicoRepository.save(servico);
    }

    private String gerarSlug(String nome) {
        if (nome == null) return "";

        return nome.toLowerCase()
                //.normalize(Normalizer.Form.NFD) // Remove acentos
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-z0-9\\s]", "") // Remove caracteres especiais
                .replaceAll("\\s+", "-")        // Substitui espaços por hifens
                .trim();
    }

    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    public Servico buscarServico(UUID id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + id));
    }

    public Servico buscarPorSlug(String slug) {
        return servicoRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
    }
}
