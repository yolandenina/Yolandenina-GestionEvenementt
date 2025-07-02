package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.entities.UserApp;
import com.evenement.gestionevenement.repository.UserAppRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppDetailsService implements UserDetailsService {
    private final UserAppRepository userAppRepository;

    public UserAppDetailsService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp app = userAppRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String role = "ROLE_" + app.getType().name();
        return new User(
                app.getEmail(),
                app.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
