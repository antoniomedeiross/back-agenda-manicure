package com.api.agenda.service;

import com.api.agenda.dto.ManicureRequestDTO;
import com.api.agenda.dto.ManicureResponseDTO;
import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        manicure.setPassword(manicureDTO.password());
        manicure.setRole("ROLE_MANICURE");

        manicureRepository.save(manicure);

        return new ManicureResponseDTO(
                manicure.getName(),
                manicure.getEmail(),
                manicure.getEspecialidade()
        );
    }

    // Read


    // Update
    // Delete
}
