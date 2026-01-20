package com.api.agenda.service;

import com.api.agenda.dto.AgendamentoRequestDTO;
import com.api.agenda.dto.AgendamentoResponseDTO;
import com.api.agenda.entity.Agendamento;
import com.api.agenda.entity.Client;
import com.api.agenda.entity.Manicure;
import com.api.agenda.entity.Servico;
import com.api.agenda.repository.AgendamentoRepository;
import com.api.agenda.repository.ClientRepository;
import com.api.agenda.repository.ManicureRepository;
import com.api.agenda.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final ManicureRepository manicureRepository;
    private final ServicoRepository servicoRepository;
    private final ClientRepository clientRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ManicureRepository manicureRepository, ServicoRepository servicoRepository, ClientRepository clientRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.manicureRepository = manicureRepository;
        this.servicoRepository = servicoRepository;
        this.clientRepository = clientRepository;
    }


    // GET
    public Page<AgendamentoResponseDTO> listarAgendamentosFuturos(Pageable pageable) {
        LocalDateTime horaAtual = LocalDateTime.now();
        Page<Agendamento> agendamentos = agendamentoRepository.findAgendamentoByDataHoraAfter(horaAtual, pageable);

        return agendamentos.map(a -> new AgendamentoResponseDTO(
                a.getClient().getName(),
                a.getManicure().getName(),
                a.getServicos()
                        .stream()
                        .map(Servico::getNome)
                        .toList(),
                a.getDataHora(),
                a.getStatus()
        ));
    }

    // POST
    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO dto) {
        Manicure manicure = manicureRepository.findById(dto.manicureId())
                .orElseThrow(() -> new EntityNotFoundException("Manicure não encontrada"));
        Client client = clientRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        List<Servico> servicos = servicoRepository.findAllById(dto.servicosIds());

        var agendamento = new Agendamento();
        agendamento.setClient(client);
        agendamento.setManicure(manicure);
        agendamento.setStatus("AGENDADO");
        agendamento.setDataHora(dto.dataHora());
        agendamento.setServicos(servicos);

        agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDTO(
                agendamento.getClient().getName(),
                agendamento.getManicure().getName(),
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getNome)
                        .toList(),
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }

    // PATCH
    @Transactional
    public AgendamentoResponseDTO atualizarParcial(UUID id, AgendamentoRequestDTO dto) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        if (dto.manicureId() != null) {
            Manicure novaManicure = manicureRepository.findById(dto.manicureId())
                    .orElseThrow(() -> new EntityNotFoundException("Manicure não encontrada"));
            agendamento.setManicure(novaManicure);
        }

        if (dto.servicosIds() != null) {
            List<Servico> novosServicos = servicoRepository.findAllById(dto.servicosIds());

            if (novosServicos.size() != dto.servicosIds().size()) {
                throw new EntityNotFoundException("Um ou mais serviços não encontrados");
            }

            agendamento.setServicos(novosServicos);
        }

        if (dto.dataHora() != null) {
            agendamento.setDataHora(dto.dataHora());
        }

        return new AgendamentoResponseDTO(
                agendamento.getClient().getName(),
                agendamento.getManicure().getName(),
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getNome)
                        .toList(),
                agendamento.getDataHora(),
                agendamento.getStatus()
        );
    }


    // DELETE
    public String deletarAgendamento(UUID agendamentoId) {
        agendamentoRepository.deleteById(agendamentoId);
        return "Agendamento deletado com sucesso!";
    }
}
