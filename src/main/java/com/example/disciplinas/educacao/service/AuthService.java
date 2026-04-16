package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.AuthResponse;
import com.example.disciplinas.educacao.dto.LoginRequest;
import com.example.disciplinas.educacao.entity.Usuario;
import com.example.disciplinas.educacao.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName()).orElseThrow();
        String nomeProfessor = usuario.getProfessor() != null ? usuario.getProfessor().getNomeCompleto() : null;
        return new AuthResponse(usuario.getUsername(), usuario.getRole().name(), nomeProfessor, "Autenticação realizada com sucesso. Use Basic Auth nas próximas requisições.");
    }

    public AuthResponse me(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        String nomeProfessor = usuario.getProfessor() != null ? usuario.getProfessor().getNomeCompleto() : null;
        return new AuthResponse(usuario.getUsername(), usuario.getRole().name(), nomeProfessor, "Usuário autenticado.");
    }
}
