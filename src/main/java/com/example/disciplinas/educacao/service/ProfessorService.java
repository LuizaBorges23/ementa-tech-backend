package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.FormacaoProfessorRequest;
import com.example.disciplinas.educacao.dto.ProfessorPortalResponse;
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
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new ResourceNotFoundException("Professor nao encontrado com id " + id));
    }

    public Professor buscarPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        if (usuario.getProfessor() == null) {
            throw new ResourceNotFoundException("Usuario nao esta vinculado a um professor");
        }
        return usuario.getProfessor();
    }

    @Transactional(readOnly = true)
    public ProfessorPortalResponse buscarPortalPorUsername(String username) {
        Professor professor = buscarPorUsername(username);

        List<ProfessorPortalResponse.FormacaoResumo> formacoes = professor.getFormacoes() == null
                ? List.of()
                : professor.getFormacoes().stream()
                .map(formacao -> new ProfessorPortalResponse.FormacaoResumo(
                        formacao.getId(),
                        formacao.getCategoriaTitulacao().name(),
                        formacao.getInstituicaoConclusao(),
                        formacao.getNomeCurso(),
                        formacao.getAnoConclusao()
                ))
                .toList();

        return new ProfessorPortalResponse(
                professor.getId(),
                professor.getMatricula(),
                professor.getNomeCompleto(),
                professor.getEmail(),
                professor.getTelefone(),
                professor.getAtivo(),
                professor.getEscola() == null
                        ? null
                        : new ProfessorPortalResponse.EscolaResumo(
                        professor.getEscola().getId(),
                        professor.getEscola().getNome()
                ),
                formacoes
        );
    }

    public Professor salvar(ProfessorRequest request) {
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola nao encontrada com id " + request.getEscolaId()));

        if (professorRepository.existsByMatricula(request.getMatricula())) {
            throw new BusinessRuleException("Ja existe professor com essa matricula");
        }

        Professor professor = new Professor();
        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setEscola(escola);
        professor = professorRepository.save(professor);

        if (request.getUsername() != null && !request.getUsername().isBlank()
                && request.getPassword() != null && !request.getPassword().isBlank()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(request.getUsername());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setRole(RoleName.ROLE_PROFESSOR);
            usuario.setEnabled(professor.getAtivo());
            usuario.setProfessor(professor);
            usuarioRepository.save(usuario);
        }

        return professor;
    }

    public Professor atualizar(Long id, ProfessorRequest request) {
        Professor professor = buscar(id);
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola nao encontrada com id " + request.getEscolaId()));

        professor.setMatricula(request.getMatricula());
        professor.setNomeCompleto(request.getNomeCompleto());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());
        professor.setAtivo(request.getAtivo() == null || request.getAtivo());
        professor.setEscola(escola);
        atualizarStatusUsuario(professor, professor.getAtivo());
        return professorRepository.save(professor);
    }

    public Professor inativar(Long id) {
        Professor professor = buscar(id);
        professor.setAtivo(false);
        atualizarStatusUsuario(professor, false);
        return professorRepository.save(professor);
    }

    public Professor ativar(Long id) {
        Professor professor = buscar(id);
        professor.setAtivo(true);
        atualizarStatusUsuario(professor, true);
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

    private void atualizarStatusUsuario(Professor professor, Boolean ativo) {
        if (professor.getUsuario() == null) {
            return;
        }

        Usuario usuario = professor.getUsuario();
        usuario.setEnabled(Boolean.TRUE.equals(ativo));
        usuarioRepository.save(usuario);
    }
}
