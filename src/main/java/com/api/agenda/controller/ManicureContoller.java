package com.api.agenda.controller;

import com.api.agenda.dto.ManicureRequestDTO;
import com.api.agenda.dto.ManicureResponseDTO;
import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manicure")
public class ManicureContoller {

    @Autowired
    ManicureRepository manicureRepository;

    @GetMapping
    public ResponseEntity<List<ManicureResponseDTO>> listarManicures() {
// Busca todas as entidades do banco
        List<Manicure> manicures = manicureRepository.findByRole("ROLE_MANICURE");

        // Converte a lista de Entity para lista de DTO
        List<ManicureResponseDTO> listaDTO = manicures.stream()
                .map(ManicureResponseDTO::new)
                .toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping
    public ResponseEntity<ManicureResponseDTO> cadastrarManicure(@RequestBody @Valid ManicureRequestDTO manicureDTO) {

        var manicure = new Manicure();
        manicure.setName(manicureDTO.name());
        manicure.setEmail(manicureDTO.email());
        manicure.setEspecialidade(manicureDTO.especialidade());
        manicure.setPassword(manicureDTO.password());
        manicure.setRole("ROLE_MANICURE");

        manicureRepository.save(manicure);

        var manicureResponseDTO = new ManicureResponseDTO(
                manicure.getName(),
                manicure.getEmail(),
                manicure.getEspecialidade()
        );

        return ResponseEntity.ok(manicureResponseDTO);
    }


}
