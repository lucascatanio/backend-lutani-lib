package br.com.lutani.lutani_lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // <-- IMPORT NECESSÁRIO
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.lutani.lutani_lib.entities.Usuario;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() 
                
                .requestMatchers("/api/usuarios/**").hasRole("ADMINISTRADOR")

                .requestMatchers(HttpMethod.POST, "/api/livros", "/api/generos").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.PUT, "/api/livros/**", "/api/generos/**").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.DELETE, "/api/livros/**", "/api/generos/**").hasRole("ADMINISTRADOR")

                .requestMatchers("/api/leitores/**").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers("/api/exemplares/**").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers("/api/emprestimos/**").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers("/api/devolucoes/**").hasAnyRole("ADMINISTRADOR", "BIBLIOTECARIO")
                
                .requestMatchers("/api/publico/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<Usuario> auditorProvider() {
        return new AuditorAwareImpl();
    }
}