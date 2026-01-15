package com.api.agenda.service;


import com.api.agenda.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if (user.isPresent()) return user.get();

//        //  Se não achou, tenta na tabela de Manicures
//        var manicure = manicureRepository.findByEmail(username);
//        if (manicure.isPresent()) return manicure.get();

        //  Se não achou em nenhum dos dois, lança erro
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}