package com.api.agenda.controller;


import com.api.agenda.dto.ClientDTO;
import com.api.agenda.dto.ClientResponseDTO;
import com.api.agenda.entity.User;
import com.api.agenda.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
    Clientes podem se cadastrar,
    Escolher os serviços
*/
@RestController()
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<ClientResponseDTO> registerClient(@RequestBody @Valid ClientDTO clientDTO) {

        ClientResponseDTO resp = clientService.saveClient(clientDTO);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("meu-perfil")
    public ResponseEntity<ClientResponseDTO> getClientProfile(@AuthenticationPrincipal User user) {

        return clientService.getClientProfile(user.getId());
    }

    @PatchMapping("/update")
    public ResponseEntity<ClientResponseDTO> updateClientProfile(@AuthenticationPrincipal User user,
                                                                     @RequestBody @Valid ClientDTO clientDTO) {
        ClientResponseDTO responseDTO = clientService.updateClientProfile(user.getId(), clientDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Cliente deletado com sucesso.");
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<String> deleteOwnAccount(@AuthenticationPrincipal User user) {
        clientService.deleteClient(user.getId());
        return ResponseEntity.ok("Sua conta foi deletada com sucesso.");
    }
}
