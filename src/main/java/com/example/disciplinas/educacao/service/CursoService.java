package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.CursoRequest;
import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.CursoRepository;
import com.example.disciplinas.educacao.repository.EscolaRepository;
import com.example.disciplinas.educacao.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EscolaRepository escolaRepository;
    private final ProfessorRepository professorRepository;

    public CursoService(CursoRepository cursoRepository, EscolaRepository escolaRepository, ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.escolaRepository = escolaRepository;
        this.professorRepository = professorRepository;
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public Curso buscar(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com id " + id));
    }

    public Curso salvar(CursoRequest request) {
        Curso curso = new Curso();
        preencher(curso, request);
        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, CursoRequest request) {
        Curso curso = buscar(id);
        preencher(curso, request);
        return cursoRepository.save(curso);
    }

    public Curso inativar(Long id) {
        Curso curso = buscar(id);
        curso.setAtivo(false);
        return cursoRepository.save(curso);
    }

    public Curso ativar(Long id) {
        Curso curso = buscar(id);
        curso.setAtivo(true);
        return cursoRepository.save(curso);
    }

    private void preencher(Curso curso, CursoRequest request) {
        Escola escola = escolaRepository.findById(request.getEscolaId())
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + request.getEscolaId()));
        Professor coordenador = null;
        if (request.getCoordenadorCursoId() != null) {
            coordenador = professorRepository.findById(request.getCoordenadorCursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor coordenador não encontrado com id " + request.getCoordenadorCursoId()));
        }

        curso.setSigla(request.getSigla());
        curso.setDescricao(request.getDescricao());
        curso.setEscola(escola);
        curso.setCoordenadorCurso(coordenador);
        curso.setDataCadastro(request.getDataCadastro() != null ? request.getDataCadastro() : LocalDate.now());
        curso.setAtivo(request.getAtivo() == null || request.getAtivo());
    }
}
