package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.DisciplinaRequest;
import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.CursoRepository;
import com.example.disciplinas.educacao.repository.DisciplinaRepository;
import com.example.disciplinas.educacao.repository.EscolaRepository;
import com.example.disciplinas.educacao.repository.ProfessorRepository;
import com.example.disciplinas.educacao.repository.ProgramaDisciplinaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final EscolaRepository escolaRepository;
    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;
    private final ProgramaDisciplinaRepository programaDisciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository,
                             EscolaRepository escolaRepository,
                             CursoRepository cursoRepository,
                             ProfessorRepository professorRepository,
                             ProgramaDisciplinaRepository programaDisciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.escolaRepository = escolaRepository;
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
        this.programaDisciplinaRepository = programaDisciplinaRepository;
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscar(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com id " + id));
    }

    public Disciplina salvar(DisciplinaRequest request) {
        Disciplina disciplina = new Disciplina();
        preencher(disciplina, request);
        Disciplina salva = disciplinaRepository.save(disciplina);
        criarProgramaInicialSeNecessario(salva);
        return salva;
    }

    public Disciplina atualizar(Long id, DisciplinaRequest request) {
        Disciplina disciplina = buscar(id);
        preencher(disciplina, request);
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina inativar(Long id) {
        Disciplina disciplina = buscar(id);
        disciplina.setAtivo(false);
        return disciplinaRepository.save(disciplina);
    }

    private void criarProgramaInicialSeNecessario(Disciplina disciplina) {
        if (!programaDisciplinaRepository.existsByDisciplinaId(disciplina.getId())) {
            ProgramaDisciplina programa = new ProgramaDisciplina();
            programa.setDisciplina(disciplina);
            programa.setSemestre(1);
            programa.setEmenta("Pendente de preenchimento.");
            programa.setCompetenciasHabilidades("Pendente de preenchimento.");
            programa.setConteudoProgramatico("Pendente de preenchimento.");
            programa.setMetodologia("Pendente de preenchimento.");
            programa.setProcessoAvaliacao("Pendente de preenchimento.");
            programa.setDataCadastro(LocalDate.now());
            programa.setAtivo(false);
            programaDisciplinaRepository.save(programa);
        }
    }

    private void preencher(Disciplina disciplina, DisciplinaRequest request) {
        if (request.getCursoIds() == null || request.getCursoIds().isEmpty()) {
            throw new BusinessRuleException("A disciplina deve estar vinculada a pelo menos um curso");
        }
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));
        List<Curso> cursos = cursoRepository.findAllById(request.getCursoIds());
        Professor professor = null;
        if (request.getProfessorId() != null) {
            professor = professorRepository.findById(request.getProfessorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com id " + request.getProfessorId()));
        }

        disciplina.setSigla(request.getSigla());
        disciplina.setDescricao(request.getDescricao());
        disciplina.setCargaHoraria(request.getCargaHoraria());
        disciplina.setEscola(escola);
        disciplina.setCursos(cursos);
        disciplina.setProfessor(professor);
        disciplina.setDataCadastro(request.getDataCadastro() != null ? request.getDataCadastro() : LocalDate.now());
        disciplina.setAtivo(request.getAtivo() == null || request.getAtivo());
    }
}
