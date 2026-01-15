package com.api.agenda.service;

import com.api.agenda.entity.Manicure;
import com.api.agenda.repository.ManicureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Value("${api.security.admin.email}")
    private String adminEmail;

    @Value("${api.security.admin.password}")
    private String adminPassword;

    @Autowired
    private ManicureRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByEmail(adminEmail).isEmpty()) {
            Manicure admin = new Manicure();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode(adminPassword));
            admin.setRole("ROLE_ADMIN"); // <--- O "superpoder"
            admin.setEspecialidade("Gestora");

            // Preencha outros campos obrigatórios (Nome, etc)
            repository.save(admin);

            // TIRAR DEPOIS
            System.out.println(">>> Administrador mestre criado com sucesso: " + adminEmail);
        }
    }
}
