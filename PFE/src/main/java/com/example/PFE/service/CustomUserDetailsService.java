package com.example.PFE.service;

import  com.example.PFE.model.Utilisateur;
import  com.example.PFE.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec email: " + email));

        // On récupère le rôle stocké dans l'utilisateur, ex: "ROLE_ADMIN"
        String role = user.getRole();
        if (role == null || role.isBlank()) {
            role = "ROLE_USER";
        }
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        // Crée un UserDetails avec le rôle
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(role)  // Spring Security attend "ROLE_XXX"
                .build();
    }
}
