package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.FormacaoProfessorRequest;
import com.example.disciplinas.educacao.dto.ProfessorRequest;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.FormacaoProfessor;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.entity.Usuario;
import com.example.disciplinas.educacao.enums.RoleName;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.EscolaRepository;
import com.example.disciplinas.educacao.repository.FormacaoProfessorRepository;
import com.example.disciplinas.educacao.repository.ProfessorRepository;
import com.example.disciplinas.educacao.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final EscolaRepository escolaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FormacaoProfessorRepository formacaoProfessorRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(ProfessorRepository professorRepository,
                            EscolaRepository escolaRepository,
                            UsuarioRepository usuarioRepository,
                            FormacaoProfessorRepository formacaoProfessorRepository,
                            PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.escolaRepository = escolaRepository;
        this.usuarioRepository = usuarioRepository;
        this.formacaoProfessorRepository = formacaoProfessorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Professor> listar() {
        return professorRepository.findAll();
    }

    public Professor buscar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com id " + id));
    }

    public Professor buscarPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        if (usuario.getProfessor() == null) {
            throw new ResourceNotFoundException("Usuário não está vinculado a um professor");
        }
        return usuario.getProfessor();
    }

    public Professor salvar(ProfessorRequest request) {
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));

        if (professorRepository.existsByMatricula(request.getMatricula())) {
            throw new BusinessRuleException("Já existe professor com essa matrícula");
        }

        Professor professor = new Professor();
        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setEscola(escola);
        professor = professorRepository.save(professor);

        if (request.getUsername() != null && !request.getUsername().isBlank() && request.getPassword() != null && !request.getPassword().isBlank()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(request.getUsername());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setRole(RoleName.ROLE_PROFESSOR);
            usuario.setEnabled(true);
            usuario.setProfessor(professor);
            usuarioRepository.save(usuario);
        }

        return professor;
    }

    public Professor atualizar(Long id, ProfessorRequest request) {
        Professor professor = buscar(id);
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));

        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setEscola(escola);
        return professorRepository.save(professor);
    }

    public Professor inativar(Long id) {
        Professor professor = buscar(id);
        professor.setAtivo(false);
        return professorRepository.save(professor);
    }

    public FormacaoProfessor adicionarFormacaoDoProfessor(String username, FormacaoProfessorRequest request) {
        Professor professor = buscarPorUsername(username);
        return adicionarFormacao(professor, request);
    }

    public FormacaoProfessor adicionarFormacaoAoProfessor(Long professorId, FormacaoProfessorRequest request) {
        Professor professor = buscar(professorId);
        return adicionarFormacao(professor, request);
    }

    private FormacaoProfessor adicionarFormacao(Professor professor, FormacaoProfessorRequest request) {
        FormacaoProfessor formacao = new FormacaoProfessor();
        formacao.setProfessor(professor);
        formacao.setCategoriaTitulacao(request.getCategoriaTitulacao());
        formacao.setInstituicaoConclusao(request.getInstituicaoConclusao());
        formacao.setNomeCurso(request.getNomeCurso());
        formacao.setAnoConclusao(request.getAnoConclusao());
        return formacaoProfessorRepository.save(formacao);
    }
}
