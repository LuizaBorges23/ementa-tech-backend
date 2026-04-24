package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.ProgramaDisciplinaRequest;
import com.example.disciplinas.educacao.dto.ProgramaDisciplinaResponse;
import com.example.disciplinas.educacao.entity.BibliografiaBasica;
import com.example.disciplinas.educacao.entity.BibliografiaComplementar;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.BibliografiaBasicaRepository;
import com.example.disciplinas.educacao.repository.BibliografiaComplementarRepository;
import com.example.disciplinas.educacao.repository.DisciplinaRepository;
import com.example.disciplinas.educacao.repository.ProgramaDisciplinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgramaDisciplinaService {

    private final ProgramaDisciplinaRepository programaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final BibliografiaBasicaRepository bibliografiaBasicaRepository;
    private final BibliografiaComplementarRepository bibliografiaComplementarRepository;

    public ProgramaDisciplinaService(ProgramaDisciplinaRepository programaRepository,
                                     DisciplinaRepository disciplinaRepository,
                                     BibliografiaBasicaRepository bibliografiaBasicaRepository,
                                     BibliografiaComplementarRepository bibliografiaComplementarRepository) {
        this.programaRepository = programaRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.bibliografiaBasicaRepository = bibliografiaBasicaRepository;
        this.bibliografiaComplementarRepository = bibliografiaComplementarRepository;
    }

    @Transactional(readOnly = true)
    public List<ProgramaDisciplinaResponse> listar() {
        return programaRepository.findAll()
                .stream()
                .map(this::mapearResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProgramaDisciplinaResponse buscar(Long id) {
        return mapearResposta(buscarEntidade(id));
    }

    public ProgramaDisciplina salvar(ProgramaDisciplinaRequest request) {
        validarProgramaAtivo(request.getDisciplinaId(), request.getAtivo(), null);
        ProgramaDisciplina programa = new ProgramaDisciplina();
        preencher(programa, request);
        return programaRepository.save(programa);
    }

    public ProgramaDisciplina atualizar(Long id, ProgramaDisciplinaRequest request) {
        validarProgramaAtivo(request.getDisciplinaId(), request.getAtivo(), id);
        ProgramaDisciplina programa = buscarEntidade(id);
        preencher(programa, request);
        return programaRepository.save(programa);
    }

    public ProgramaDisciplina inativar(Long id) {
        ProgramaDisciplina programa = buscarEntidade(id);
        programa.setAtivo(false);
        return programaRepository.save(programa);
    }

    public List<ProgramaDisciplina> listarDoProfessor(String username, ProfessorService professorService) {
        Long professorId = professorService.buscarPorUsername(username).getId();
        return programaRepository.findByDisciplinaProfessorId(professorId);
    }

    @Transactional(readOnly = true)
    public List<ProgramaDisciplinaResponse> listarDoProfessorDetalhado(String username, ProfessorService professorService) {
        Long professorId = professorService.buscarPorUsername(username).getId();
        return programaRepository.findByDisciplinaProfessorId(professorId)
                .stream()
                .map(this::mapearResposta)
                .toList();
    }

    public List<ProgramaDisciplina> listarIncompletos() {
        return programaRepository.findAll().stream().filter(this::isIncompleto).toList();
    }

    public List<ProgramaDisciplina> listarIncompletosDoProfessor(String username, ProfessorService professorService) {
        Long professorId = professorService.buscarPorUsername(username).getId();
        return programaRepository.findByAtivoTrueAndDisciplinaProfessorId(professorId)
                .stream()
                .filter(this::isIncompleto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramaDisciplinaResponse> listarIncompletosDoProfessorDetalhado(String username, ProfessorService professorService) {
        Long professorId = professorService.buscarPorUsername(username).getId();
        return programaRepository.findByAtivoTrueAndDisciplinaProfessorId(professorId)
                .stream()
                .filter(this::isIncompleto)
                .map(this::mapearResposta)
                .toList();
    }

    public void validarProgramaDoProfessor(Long programaId, String username, ProfessorService professorService) {
        ProgramaDisciplina programa = buscarEntidade(programaId);
        Long professorId = professorService.buscarPorUsername(username).getId();
        if (programa.getDisciplina().getProfessor() == null || !programa.getDisciplina().getProfessor().getId().equals(professorId)) {
            throw new BusinessRuleException("O programa informado nao esta relacionado ao professor autenticado");
        }
    }

    public boolean isIncompleto(ProgramaDisciplina programa) {
        long basicas = bibliografiaBasicaRepository.countByProgramaDisciplinaId(programa.getId());
        long complementares = bibliografiaComplementarRepository.countByProgramaDisciplinaId(programa.getId());
        return textoVazio(programa.getEmenta())
                || textoVazio(programa.getCompetenciasHabilidades())
                || textoVazio(programa.getConteudoProgramatico())
                || textoVazio(programa.getMetodologia())
                || textoVazio(programa.getProcessoAvaliacao())
                || basicas < 3
                || complementares < 5;
    }

    private boolean textoVazio(String valor) {
        return valor == null || valor.isBlank();
    }

    private void validarProgramaAtivo(Long disciplinaId, Boolean ativo, Long idAtual) {
        if (Boolean.TRUE.equals(ativo)) {
            boolean existe = idAtual == null
                    ? programaRepository.existsByDisciplinaIdAndAtivoTrue(disciplinaId)
                    : programaRepository.existsByDisciplinaIdAndAtivoTrueAndIdNot(disciplinaId, idAtual);
            if (existe) {
                throw new BusinessRuleException("Uma disciplina so pode possuir um unico programa ativo");
            }
        }
    }

    private void preencher(ProgramaDisciplina programa, ProgramaDisciplinaRequest request) {
        Disciplina disciplina = disciplinaRepository.findById(request.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina nao encontrada com id " + request.getDisciplinaId()));
        List<Disciplina> prerequisitos = disciplinaRepository.findAllById(request.getPrerequisitoIds());
        programa.setDisciplina(disciplina);
        programa.setSemestre(request.getSemestre());
        programa.setEmenta(request.getEmenta());
        programa.setCompetenciasHabilidades(request.getCompetenciasHabilidades());
        programa.setConteudoProgramatico(request.getConteudoProgramatico());
        programa.setMetodologia(request.getMetodologia());
        programa.setProcessoAvaliacao(request.getProcessoAvaliacao());
        programa.setDataCadastro(request.getDataCadastro() != null ? request.getDataCadastro() : LocalDate.now());
        programa.setAtivo(request.getAtivo() == null || request.getAtivo());
        programa.setPrerequisitos(prerequisitos);
    }

    @Transactional(readOnly = true)
    public ProgramaDisciplina buscarEntidade(Long id) {
        return programaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programa de disciplina nao encontrado com id " + id));
    }

    private ProgramaDisciplinaResponse mapearResposta(ProgramaDisciplina programa) {
        Disciplina disciplina = programa.getDisciplina();

        List<ProgramaDisciplinaResponse.CursoReferencia> cursos = disciplina.getCursos() == null
                ? List.of()
                : disciplina.getCursos().stream()
                .map(curso -> new ProgramaDisciplinaResponse.CursoReferencia(
                        curso.getId(),
                        curso.getSigla(),
                        curso.getDescricao()
                ))
                .toList();

        ProgramaDisciplinaResponse.DisciplinaDetalhe disciplinaDetalhe = new ProgramaDisciplinaResponse.DisciplinaDetalhe(
                disciplina.getId(),
                disciplina.getSigla(),
                disciplina.getDescricao(),
                disciplina.getCargaHoraria(),
                disciplina.getAtivo(),
                disciplina.getEscola() == null
                        ? null
                        : new ProgramaDisciplinaResponse.EscolaReferencia(
                        disciplina.getEscola().getId(),
                        disciplina.getEscola().getNome()
                ),
                disciplina.getProfessor() == null
                        ? null
                        : new ProgramaDisciplinaResponse.ProfessorReferencia(
                        disciplina.getProfessor().getId(),
                        disciplina.getProfessor().getNomeCompleto()
                ),
                cursos
        );

        List<ProgramaDisciplinaResponse.DisciplinaReferencia> prerequisitos = programa.getPrerequisitos() == null
                ? List.of()
                : programa.getPrerequisitos().stream()
                .map(item -> new ProgramaDisciplinaResponse.DisciplinaReferencia(
                        item.getId(),
                        item.getSigla(),
                        item.getDescricao()
                ))
                .toList();

        List<ProgramaDisciplinaResponse.BibliografiaDetalhe> bibliografiasBasicas =
                bibliografiaBasicaRepository.findByProgramaDisciplinaIdOrderByIdAsc(programa.getId())
                        .stream()
                        .map(this::mapearBibliografiaBasica)
                        .toList();

        List<ProgramaDisciplinaResponse.BibliografiaDetalhe> bibliografiasComplementares =
                bibliografiaComplementarRepository.findByProgramaDisciplinaIdOrderByIdAsc(programa.getId())
                        .stream()
                        .map(this::mapearBibliografiaComplementar)
                        .toList();

        return new ProgramaDisciplinaResponse(
                programa.getId(),
                programa.getSemestre(),
                programa.getEmenta(),
                programa.getCompetenciasHabilidades(),
                programa.getConteudoProgramatico(),
                programa.getMetodologia(),
                programa.getProcessoAvaliacao(),
                programa.getDataCadastro(),
                programa.getAtivo(),
                disciplinaDetalhe,
                prerequisitos,
                bibliografiasBasicas,
                bibliografiasComplementares
        );
    }

    private ProgramaDisciplinaResponse.BibliografiaDetalhe mapearBibliografiaBasica(BibliografiaBasica bibliografia) {
        return new ProgramaDisciplinaResponse.BibliografiaDetalhe(
                bibliografia.getId(),
                bibliografia.getTitulo(),
                bibliografia.getAutores(),
                bibliografia.getEditora(),
                bibliografia.getIsbn(),
                bibliografia.getAnoPublicacao(),
                bibliografia.getLocalizacao(),
                bibliografia.getLinkLivro(),
                bibliografia.getPosicaoEstante()
        );
    }

    private ProgramaDisciplinaResponse.BibliografiaDetalhe mapearBibliografiaComplementar(BibliografiaComplementar bibliografia) {
        return new ProgramaDisciplinaResponse.BibliografiaDetalhe(
                bibliografia.getId(),
                bibliografia.getTitulo(),
                bibliografia.getAutores(),
                bibliografia.getEditora(),
                bibliografia.getIsbn(),
                bibliografia.getAnoPublicacao(),
                bibliografia.getLocalizacao(),
                bibliografia.getLinkLivro(),
                bibliografia.getPosicaoEstante()
        );
    }
}
