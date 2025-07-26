package br.com.lutani.lutani_lib.config; // Verifique seu package

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lutani.lutani_lib.entities.Usuario;

// A classe agora implementa AuditorAware<Usuario>
public class AuditorAwareImpl implements AuditorAware<Usuario> {

    @Override
    public Optional<Usuario> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .filter(auth -> !"anonymousUser".equals(auth.getPrincipal()))
                .map(authentication -> (Usuario) authentication.getPrincipal());
    }
}