package com.example.PFE.controller;

import com.example.PFE.model.AuthRequest;
import com.example.PFE.model.Utilisateur;
import com.example.PFE.repository.UtilisateurRepository;
import com.example.PFE.service.EmailService;
import com.example.PFE.service.JwtService;
import com.example.PFE.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.PFE.dto.UtilisateurDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UtilisateurRepository repo;
    public AuthController(UtilisateurRepository repo){ this.repo = repo; }

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;




    // ✅ Inscription avec DTO + vérif mot de passe + notRobot
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UtilisateurDTO dto) {
        try {
            System.out.println("📥 Reçu DTO : " + dto);

            // 🔐 Vérification des mots de passe
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                System.out.println("❌ Les mots de passe ne correspondent pas.");
                return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas.");
            }

            // 🔐 Vérification "Je ne suis pas un robot"
         //   System.out.println("✅ isNotRobot reçu : " + dto.getIsNotRobot());
          //  if (!dto.getIsNotRobot()) {
           //     System.out.println("❌ Utilisateur n’a pas confirmé qu’il n’est pas un robot.");
           //     return ResponseEntity.badRequest().body("Vous devez confirmer que vous n'êtes pas un robotttttttttt.");
          //  }

            // 🔁 Mapping DTO vers Utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(dto.getNom());
            utilisateur.setPrenom(dto.getPrenom());
            utilisateur.setEmail(dto.getEmail());
            utilisateur.setPassword(dto.getPassword());
            utilisateur.setConfirmPassword(dto.getConfirmPassword());
            utilisateur.setPays(dto.getPays());
            utilisateur.setNumero(dto.getNumero());
            utilisateur.setDateNaissance(dto.getDateNaissance());

            String role = dto.getRole();

// ✅ rôle obligatoire
            if (role == null || role.isBlank()) {
                return ResponseEntity.badRequest().body("Le rôle est obligatoire");
            }

// ✅ normaliser
            role = role.toUpperCase().trim();
            if (!role.startsWith("ROLE_")) role = "ROLE_" + role;

// ✅ whitelist des rôles autorisés
            if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_CLIENT") && !role.equals("ROLE_TECHNICIEN")&& !role.equals("ROLE_SECRETAIRE")) {
                return ResponseEntity.badRequest().body("Rôle invalide");
            }

            utilisateur.setRole(role);

            //   utilisateur.setIsNotRobot(dto.getIsNotRobot());

            System.out.println("✅ Utilisateur construit : " + utilisateur);

            // 💾 Enregistrement
            String msg = utilisateurService.register(utilisateur);
            System.out.println("✅ Utilisateur enregistré avec succès : " + utilisateur.getEmail());

            return ResponseEntity.ok(msg);

        } catch (Exception e) {
            System.out.println("❌ Exception lors de l'enregistrement : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Si tu stockes des tokens : ajouter à une blacklist
        // Sinon, côté frontend tu supprimes le token du stockage

        return ResponseEntity.ok("Déconnexion réussie");}
    // Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Authentification
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Génération du token
            String token = jwtService.generateToken(request.getEmail());

            // Récupération de l'utilisateur complet
            Utilisateur user = utilisateurService.findByEmail(request.getEmail());

            // Préparation de la réponse JSON avec token et rôle
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());  // Assure-toi que Utilisateur a getRole()

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        } //catch (Exception e) {
          //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d’authentification");
       // }
    catch (Exception e) {
        e.printStackTrace(); // ← ajoute ça
        System.out.println("❌ Erreur login : " + e.getClass().getName() + " - " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur: " + e.getMessage()); // ← affiche le vrai message
    }
    }

    // Récupérer tous les utilisateurs
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<Utilisateur> users = utilisateurService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des utilisateurs");
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        Utilisateur user = utilisateurService.findByEmail(email);

        // Générer code 6 chiffres
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        user.setResetCode(code);
        user.setResetCodeExpiration(System.currentTimeMillis() + 10 * 60 * 1000); // 10 min

        utilisateurService.save(user);

        emailService.sendResetCode(email, code);

        return ResponseEntity.ok("Code envoyé par email");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String email,
            @RequestParam String code,
            @RequestParam String newPassword
    ) {

        Utilisateur user = utilisateurService.findByEmail(email);

        if (!user.getResetCode().equals(code)) {
            return ResponseEntity.badRequest().body("Code invalide");
        }

        if (user.getResetCodeExpiration() < System.currentTimeMillis()) {
            return ResponseEntity.badRequest().body("Code expiré");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetCode(null);
        user.setResetCodeExpiration(null);

        utilisateurService.save(user);

        return ResponseEntity.ok("Mot de passe mis à jour");
    }


        @GetMapping
        public List<Utilisateur> list(@RequestParam(required = false) String role) {
            if (role == null || role.isBlank()) {
                return repo.findAll();
            }
            return repo.findByRole(role);
        }
    }
