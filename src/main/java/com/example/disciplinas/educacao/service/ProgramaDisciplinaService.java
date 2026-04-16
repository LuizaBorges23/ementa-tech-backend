package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.ProgramaDisciplinaRequest;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.BibliografiaBasicaRepository;
import com.example.disciplinas.educacao.repository.BibliografiaComplementarRepository;
import com.example.disciplinas.educacao.repository.DisciplinaRepository;
import com.example.disciplinas.educacao.repository.ProgramaDisciplinaRepository;
import org.springframework.stereotype.Service;

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

    public List<ProgramaDisciplina> listar() {
        return programaRepository.findAll();
    }

    public ProgramaDisciplina buscar(Long id) {
        return programaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programa de disciplina não encontrado com id " + id));
    }

    public ProgramaDisciplina salvar(ProgramaDisciplinaRequest request) {
        validarProgramaAtivo(request.getDisciplinaId(), request.getAtivo(), null);
        ProgramaDisciplina programa = new ProgramaDisciplina();
        preencher(programa, request);
        return programaRepository.save(programa);
    }

    public ProgramaDisciplina atualizar(Long id, ProgramaDisciplinaRequest request) {
        validarProgramaAtivo(request.getDisciplinaId(), request.getAtivo(), id);
        ProgramaDisciplina programa = buscar(id);
        preencher(programa, request);
        return programaRepository.save(programa);
    }

    public ProgramaDisciplina inativar(Long id) {
        ProgramaDisciplina programa = buscar(id);
        programa.setAtivo(false);
        return programaRepository.save(programa);
    }

    public List<ProgramaDisciplina> listarDoProfessor(String username, ProfessorService professorService) {
        Long professorId = professorService.buscarPorUsername(username).getId();
        return programaRepository.findByDisciplinaProfessorId(professorId);
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



    public void validarProgramaDoProfessor(Long programaId, String username, ProfessorService professorService) {
        ProgramaDisciplina programa = buscar(programaId);
        Long professorId = professorService.buscarPorUsername(username).getId();
        if (programa.getDisciplina().getProfessor() == null || !programa.getDisciplina().getProfessor().getId().equals(professorId)) {
            throw new BusinessRuleException("O programa informado não está relacionado ao professor autenticado");
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
                throw new BusinessRuleException("Uma disciplina só pode possuir um único programa ativo");
            }
        }
    }

    private void preencher(ProgramaDisciplina programa, ProgramaDisciplinaRequest request) {
        Disciplina disciplina = disciplinaRepository.findById(request.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com id " + request.getDisciplinaId()));
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
}
