package com.example.PFE.service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.core.env.Environment;

import  com.example.PFE.model.Utilisateur;
import  com.example.PFE.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {
    // Ajoute cette méthode
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec email: " + email));
    }
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Environment env;

    public String register(Utilisateur utilisateur) {

        if (utilisateur.getEmail() == null || utilisateur.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'adresse email est obligatoire.");
        }

        String email = utilisateur.getEmail().toLowerCase().trim();
        utilisateur.setEmail(email);

        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        String regexGmailStrict = "^[a-zA-Z0-9]+(?:[._%+-][a-zA-Z0-9]+)*@gmail\\.com$";
        if (!email.matches(regexGmailStrict)) {
            throw new IllegalArgumentException("L'adresse email doit être une adresse Gmail valide.");
        }

        String plainPwd = utilisateur.getPassword();
        if (plainPwd == null || plainPwd.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }

        // ✅ confirmPassword obligatoire + égal
        String conf = utilisateur.getConfirmPassword();
        if (conf == null || !plainPwd.equals(conf)) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }

        // ✅ mot de passe fort (AVANT encode)
        String regexStrongPwd =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        if (!plainPwd.matches(regexStrongPwd)) {
            throw new IllegalArgumentException(
                    "Mot de passe faible: min 8 caractères, 1 majuscule, 1 minuscule, 1 chiffre, 1 caractère spécial (@$!%*?&#)."
            );
        }
      //  System.out.println("✅ isNotRobot reçu = " + utilisateur.getIsNotRobot());

        // ✅ robot obligatoire (maintenant getter correct)
      //  if (utilisateur.getIsNotRobot() == null || !utilisateur.getIsNotRobot()) {
       //     throw new IllegalArgumentException("Vous devez confirmer que vous n'êtes pas un robot.");
   //     }

        // ✅ encode UNE seule fois
        utilisateur.setPassword(passwordEncoder.encode(plainPwd));
        String role = utilisateur.getRole();
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Le rôle est obligatoire.");
        }

        role = role.toUpperCase().trim();
        if (!role.startsWith("ROLE_")) role = "ROLE_" + role;

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_CLIENT") && !role.equals("ROLE_TECHNICIEN") && !role.equals("ROLE_SECRETAIRE")) {
            throw new IllegalArgumentException("Rôle invalide.");
        }

        utilisateur.setRole(role);
        utilisateur.setConfirmPassword(null);

        utilisateurRepository.save(utilisateur);

        return "Utilisateur enregistré avec succès";
    }


    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUserById(String id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur updateUser(String id, Utilisateur updatedUser) {
        return utilisateurRepository.findById(id).map(user -> {
            user.setNom(updatedUser.getNom());
            user.setEmail(updatedUser.getEmail());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            return utilisateurRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void deleteUser(String id) {
        utilisateurRepository.deleteById(id);
    }
    public Utilisateur save(Utilisateur user) {
        return utilisateurRepository.save(user);
    }

}
