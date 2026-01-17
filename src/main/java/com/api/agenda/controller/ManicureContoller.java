package com.api.agenda.controller;

import com.api.agenda.dto.ManicureRequestDTO;
import com.api.agenda.dto.ManicureResponseDTO;
import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import com.api.agenda.service.ManicureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manicures")
public class ManicureContoller {

    @Autowired
    private ManicureService manicureService;

    @GetMapping
    public ResponseEntity<List<ManicureResponseDTO>> listarManicures() {
        List<ManicureResponseDTO> listaManicures = manicureService.listarManicures();

        return ResponseEntity.ok(listaManicures);
    }

    @PostMapping
    public ResponseEntity<ManicureResponseDTO> cadastrarManicure(@RequestBody @Valid ManicureRequestDTO manicureDTO) {
        ManicureResponseDTO manicureResponseDTO = manicureService.cadastroManicure(manicureDTO);

        return ResponseEntity.ok(manicureResponseDTO);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<ManicureResponseDTO> editarManicure(@PathVariable UUID id ,
                                                              @RequestBody @Valid ManicureRequestDTO manicureDTO) {
        ManicureResponseDTO updatedManicure = manicureService.editManicure(id, manicureDTO);

        return ResponseEntity.ok(updatedManicure);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletManicure (@PathVariable UUID id) {
        String response = manicureService.deleteManicure(id);

        return ResponseEntity.ok(response);
    }

}
