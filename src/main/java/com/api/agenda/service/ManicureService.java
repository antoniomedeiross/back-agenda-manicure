package com.api.agenda.service;

import com.api.agenda.dto.ManicureRequestDTO;
import com.api.agenda.dto.ManicureResponseDTO;
import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ManicureService {
    private final ManicureRepository manicureRepository;
    private final PasswordEncoder passwordEncoder;

    public ManicureService(ManicureRepository manicureRepository, PasswordEncoder passwordEncoder) {
        this.manicureRepository = manicureRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ManicureResponseDTO> listarManicures() {
        List<Manicure> manicures = manicureRepository.findByRole("ROLE_MANICURE");

        // Converte a lista de Entity para lista de DTO
        return manicures.stream()
                .map(ManicureResponseDTO::new)
                .toList();
    }


    @Transactional
    public ManicureResponseDTO cadastroManicure(ManicureRequestDTO manicureDTO) {
        var manicure = new Manicure();
        manicure.setName(manicureDTO.name());
        manicure.setEmail(manicureDTO.email());
        manicure.setEspecialidade(manicureDTO.especialidade());
        manicure.setPassword(passwordEncoder.encode(manicureDTO.password()));
        manicure.setRole("ROLE_MANICURE");

        manicureRepository.save(manicure);

        return new ManicureResponseDTO(
                manicure.getName(),
                manicure.getEmail(),
                manicure.getEspecialidade()
        );
    }

    // Read
    public ManicureResponseDTO getManicure(UUID id) {
        var manicure = manicureRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manicure não encontrada"));

        return new ManicureResponseDTO(
                manicure.getName(),
                manicure.getEmail(),
                manicure.getEspecialidade()
        );
    }

    // Update
    @Transactional
    public ManicureResponseDTO editManicure(UUID id, @NonNull ManicureRequestDTO manicureDTO) {
        var manicure = manicureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Manicure não encontrada"));

        if(manicureDTO.name() != null ){
            manicure.setName(manicureDTO.name());
        }
        if(manicureDTO.email() != null ) {
            manicure.setEmail(manicureDTO.email());
        }
        if(manicureDTO.especialidade() != null ) {
            manicure.setEspecialidade(manicureDTO.especialidade());
        }
        if (manicureDTO.password() != null && !manicureDTO.password().isEmpty()) {
            manicure.setPassword(passwordEncoder.encode(manicureDTO.password()));
        }

        manicureRepository.save(manicure);

        return new ManicureResponseDTO(
                manicure.getName(),
                manicure.getEmail(),
                manicure.getEspecialidade()
        );
    }

    // Delete
    public String deleteManicure(UUID id){
        var manicure = manicureRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Manicure nao encontrada"));

        manicureRepository.delete(manicure);

        return "Manicure deletada com sucesso";
    }
}
