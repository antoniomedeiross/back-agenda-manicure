package com.api.agenda.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    // Security filter chain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/client/cadastro").permitAll() // qualquer um pode se cadastrar
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // qualquer um pode acessar o login

                        .requestMatchers(HttpMethod.GET, "/services").permitAll() // qualquer um pode listar serviços
                        .requestMatchers(HttpMethod.POST, "/services").hasAnyRole("MANICURE", "ADMIN") // so manicures e admin podem cadastrar serviços
                        .requestMatchers(HttpMethod.PATCH, "/services/**").hasAnyRole("MANICURE", "ADMIN") // so manicures e admin podem atualizar serviços
                        .requestMatchers(HttpMethod.DELETE, "/services/**").hasAnyRole("MANICURE", "ADMIN") // so manicures e admin podem deletar serviços

                        .requestMatchers(HttpMethod.POST, "/manicures").hasRole("ADMIN") // so ADMIN podem cadastrar manicures
                        .requestMatchers(HttpMethod.GET, "/manicures").permitAll() // qualquer um pode listar manicures
                        .anyRequest().authenticated() // todas as outras requisições precisam estar autenticadas
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }


    // No SecurityConfig.java
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

