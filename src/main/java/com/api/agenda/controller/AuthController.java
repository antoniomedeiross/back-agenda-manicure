package com.api.agenda.controller;

import com.api.agenda.dto.AuthenticationDTO;
import com.api.agenda.dto.LoginResponseDTO;
import com.api.agenda.entity.Client;
import com.api.agenda.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationConfiguration authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationConfiguration authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO data) {

        var authToken = new UsernamePasswordAuthenticationToken(
                data.email(), data.password()
        );
// O AuthenticationManager vai usar o seu UserDetailsService customizado
        var auth = authenticationManager.getAuthenticationManager().authenticate(authToken);

        // Cast para a interface comum, não para uma classe específica
        var user = (UserDetails) auth.getPrincipal();

        var token = tokenService.generateToken(Objects.requireNonNull(user));

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


}

