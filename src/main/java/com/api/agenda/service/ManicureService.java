package com.api.agenda.service;

import com.api.agenda.dto.ManicureRequestDTO;
import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManicureService {
    private final ManicureRepository manicureRepository;
    private final PasswordEncoder passwordEncoder;

    public ManicureService(ManicureRepository manicureRepository, PasswordEncoder passwordEncoder) {
        this.manicureRepository = manicureRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastroManicure(ManicureRequestDTO dto) {
        if(manicureRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Erro: Este e-mail já está em uso.");
        }

        Manicure m = new Manicure();
        m.setName(dto.name());
        m.setEmail(dto.email());
        m.setPassword(passwordEncoder.encode(dto.password()));
        m.setEspecialidade(dto.especialidade());
        m.setRole("ROLE_MANICURE");

    }
}
