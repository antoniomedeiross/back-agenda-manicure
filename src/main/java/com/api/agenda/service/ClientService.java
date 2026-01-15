package com.api.agenda.service;

import com.api.agenda.dto.ClientDTO;
import com.api.agenda.dto.ClientResponseDTO;
import com.api.agenda.entity.Client;
import com.api.agenda.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder; // Opcional: para criptografar a senha

    public ClientService(ClientRepository repository, PasswordEncoder passwordEncoder) {
        this.clientRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional // Garante que se algo falhar, nada seja salvo no banco
    public void saveClient(ClientDTO dto) {

        // verifica se o email já está em uso
        if (clientRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Erro: Este e-mail já está em uso.");
        }

        // 2. Mapeamento DTO -> Entity
        Client client = new Client();
        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setPhone(dto.phone());
        client.setRole("ROLE_CLIENT");

        // 3. Criptografia de Senha (Segurança básica)
        client.setPassword(passwordEncoder.encode(dto.password()));

        // 4. Persistência
        clientRepository.save(client);
    }

    public ResponseEntity<ClientResponseDTO> getClientProfile(UUID id) {
        Client cliente = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        ClientResponseDTO dto = new ClientResponseDTO(
                cliente.getName(),
                cliente.getEmail(),
                cliente.getPhone()
        );

        return ResponseEntity.ok(dto);
    }
}
