//package com.example.PFE.config;
//
//import  com.example.PFE.service.CustomUserDetailsService;
//import  com.example.PFE.config.JwtAuthenticationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.*;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.*;
//
//import java.util.List;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService,
//                                                            PasswordEncoder passwordEncoder) {
//
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider(userDetailsService);
//
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS activé ici
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//
//                        // ✅ login public
//                        .requestMatchers("/api/auth/login").permitAll()
//                        .requestMatchers("/api/auth/register").hasRole("ADMIN")
//                        .requestMatchers("/api/suivi-itp/**").permitAll()
//                        .requestMatchers("/api/suivi-plan-action/**").permitAll()
//                        .requestMatchers("/api/ass-tech/**").permitAll()
//                        .requestMatchers("/api/diagnostics/**").permitAll()
//                        .requestMatchers("/api/formations/**").permitAll()
//                        .requestMatchers("/api/general-resume/**").permitAll()
//                        .requestMatchers("/api/hj-summary/**").permitAll()
//                        .requestMatchers("/api/scan/**").permitAll()
//                        .requestMatchers("/api/tissus/**").permitAll()
//                        .requestMatchers("/api/machines/**").permitAll()
//                        .requestMatchers("/api/prediction/**").permitAll()
//
//                        .requestMatchers("/api/auth/forgot-password").permitAll()
//                        .requestMatchers("/api/auth/reset-password").permitAll()
//                        .requestMatchers("/api/messages/client/**").authenticated()
//                        .requestMatchers("/api/messages/admin/**").hasRole("ADMIN")
//                        // ✅ register seulement ADMIN
//                        .requestMatchers("/api/energy/**").permitAll()
//                        .requestMatchers("/ws-energy/**").permitAll()
//                        .requestMatchers("/api/auth/register").permitAll()//.hasRole("ADMIN")
//                        .requestMatchers("/api/water/**").permitAll()
//                        .requestMatchers("/ws-water/**").permitAll()
//                        // (optionnel) logout public si tu veux
//                        .requestMatchers("/api/auth/logout").permitAll()
//
//                        .requestMatchers("/api/rappels/**").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder()))
//
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // ✅ Ajout de configuration CORS personnalisée
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOriginPatterns(List.of(
//                "http://localhost:*",
//                "http://192.168.*.*:*",
//                "http://172.16.*.*:*",
//                "capacitor://localhost",
//                "ionic://localhost",
//                "https://*.koyeb.app"
//        ));
//
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
package com.example.PFE.config;

import  com.example.PFE.service.CustomUserDetailsService;
import  com.example.PFE.config.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService,
                                                            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS activé ici
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ login public
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/suivi-itp/**").permitAll()
                        .requestMatchers("/api/suivi-plan-action/**").permitAll()
                        .requestMatchers("/api/ass-tech/**").permitAll()
                        .requestMatchers("/api/diagnostics/**").permitAll()
                        .requestMatchers("/api/formations/**").permitAll()
                        .requestMatchers("/api/general-resume/**").permitAll()
                        .requestMatchers("/api/hj-summary/**").permitAll()
                        .requestMatchers("/api/scan/**").permitAll()
                        .requestMatchers("/api/tissus/**").permitAll()
                        .requestMatchers("/api/machines/**").permitAll()
                        .requestMatchers("/api/prediction/**").permitAll()
                        .requestMatchers("/api/entreprises/**").permitAll()
                        .requestMatchers("/api/complements/**").permitAll()
                        .requestMatchers("/api/auth/forgot-password").permitAll()
                        .requestMatchers("/api/auth/reset-password").permitAll()
                        .requestMatchers("/api/messages/client/**").authenticated()
                        .requestMatchers("/api/messages/admin/**").hasRole("ADMIN")
                        // ✅ register seulement ADMIN
                        .requestMatchers("/api/energy/**").permitAll()
                        .requestMatchers("/ws-energy/**").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()//.hasRole("ADMIN")
                        .requestMatchers("/api/water/**").permitAll()
                        .requestMatchers("/ws-water/**").permitAll()
                        // (optionnel) logout public si tu veux
                        .requestMatchers("/api/auth/logout").permitAll()

                        .requestMatchers("/api/rappels/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder()))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Ajout de configuration CORS personnalisée
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:8100",
//                "http://localhost:5173",// ✅ Frontend sur navigateur
//                "http://localhost:5173/",
//                "http://192.168.100.71:8100",
//                "http://192.168.43.69:8100",
//                "http://192.168.0.71:8100",
//                "http://20.20.24.201:8100", // ✅ fac
//                "http://172.16.0.29:8100", // ✅ dar
//                "capacitor://localhost",       // ✅ App mobile Ionic/Capacitor
//                "http://localhost"  ,
//                "capacitor-electron://-",
//                "http://172.16.0.38:8100" ,
//                "http://localhost",
//                "https://localhost",        // ✅ à ajouter
//                "ionic://localhost",        // ✅ à ajouter (anciens projets Ionic)
//                "null"    // ✅ fallback localhost
//        ));
//        configuration.addAllowedMethod("*");
//        //configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        //configuration.setAllowedHeaders(List.of("*"));
//        //configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
//
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://192.168.*.*:*",
                "http://172.16.*.*:*",
                "http://10.*.*.*:*",
                "capacitor://localhost",
                "ionic://localhost",
                "capacitor-electron://*",
                "file://*"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
