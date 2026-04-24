package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.AuthResponse;
import com.example.disciplinas.educacao.dto.LoginRequest;
import com.example.disciplinas.educacao.entity.Usuario;
import com.example.disciplinas.educacao.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse login(LoginRequest request) {
        validarProfessorAtivo(request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        return buildResponse(authentication, "AutenticaÃ§Ã£o realizada com sucesso. Use Basic Auth nas prÃ³ximas requisiÃ§Ãµes.");
    }

    public AuthResponse me(Authentication authentication) {
        return buildResponse(authentication, "UsuÃ¡rio autenticado.");
    }

    private AuthResponse buildResponse(Authentication authentication, String mensagem) {
        return usuarioRepository.findByUsername(authentication.getName())
                .map(usuario -> fromUsuario(usuario, mensagem))
                .orElseGet(() -> fromAuthentication(authentication, mensagem));
    }

    private AuthResponse fromUsuario(Usuario usuario, String mensagem) {
        String nomeProfessor = usuario.getProfessor() != null ? usuario.getProfessor().getNomeCompleto() : null;
        return new AuthResponse(usuario.getUsername(), usuario.getRole().name(), nomeProfessor, mensagem);
    }

    private AuthResponse fromAuthentication(Authentication authentication, String mensagem) {
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_PROFESSOR");

        String nomeProfessor = "ROLE_PROFESSOR".equals(role) ? "Professor Exemplo" : null;
        return new AuthResponse(authentication.getName(), role, nomeProfessor, mensagem);
    }

    private void validarProfessorAtivo(String username) {
        usuarioRepository.findByUsername(username)
                .filter(usuario -> usuario.getProfessor() != null)
                .filter(usuario -> Boolean.FALSE.equals(usuario.getProfessor().getAtivo()))
                .ifPresent(usuario -> {
                    throw new DisabledException("Este professor esta inativo. Procure o administrador para reativar o acesso.");
                });
    }
}
