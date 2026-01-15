package com.api.agenda.controller;


import com.api.agenda.dto.ClientDTO;
import com.api.agenda.dto.ClientResponseDTO;
import com.api.agenda.entity.User;
import com.api.agenda.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ClientDTO> registerClient(@RequestBody @Valid ClientDTO clientDTO) {

        clientService.saveClient(clientDTO);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping("meu-perfil")
    public ResponseEntity<ClientResponseDTO> getClientProfile(@AuthenticationPrincipal User user) {

        return clientService.getClientProfile(user.getId());
    }

}
