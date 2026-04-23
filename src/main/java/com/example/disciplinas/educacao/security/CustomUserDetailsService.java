package com.example.disciplinas.educacao.security;

import com.example.disciplinas.educacao.entity.Usuario;
import com.example.disciplinas.educacao.enums.RoleName;
import com.example.disciplinas.educacao.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String PROFESSOR_USERNAME = "professor1";
    private static final String PROFESSOR_PASSWORD = "prof123";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .map(this::buildUserDetails)
                .orElseGet(() -> buildFallbackUser(username));
    }

    private UserDetails buildUserDetails(Usuario usuario) {
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
        );
    }

    private UserDetails buildFallbackUser(String username) {
        if (ADMIN_USERNAME.equals(username)) {
            return new User(
                    ADMIN_USERNAME,
                    passwordEncoder.encode(ADMIN_PASSWORD),
                    List.of(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()))
            );
        }

        if (PROFESSOR_USERNAME.equals(username)) {
            return new User(
                    PROFESSOR_USERNAME,
                    passwordEncoder.encode(PROFESSOR_PASSWORD),
                    List.of(new SimpleGrantedAuthority(RoleName.ROLE_PROFESSOR.name()))
            );
        }

        throw new UsernameNotFoundException("UsuÃ¡rio nÃ£o encontrado");
    }
}
