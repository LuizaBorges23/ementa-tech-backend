package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.MatrizRequest;
import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.entity.Matriz;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.CursoRepository;
import com.example.disciplinas.educacao.repository.DisciplinaRepository;
import com.example.disciplinas.educacao.repository.MatrizRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatrizService {

    private final MatrizRepository matrizRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public MatrizService(MatrizRepository matrizRepository, CursoRepository cursoRepository, DisciplinaRepository disciplinaRepository) {
        this.matrizRepository = matrizRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<Matriz> listar() {
        return matrizRepository.findAll();
    }

    public Matriz buscar(Long id) {
        return matrizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matriz não encontrada com id " + id));
    }

    public Matriz salvar(MatrizRequest request) {
        validarMatrizAtiva(request.getCursoId(), request.getAtivo(), null);
        Matriz matriz = new Matriz();
        preencher(matriz, request);
        return matrizRepository.save(matriz);
    }

    public Matriz atualizar(Long id, MatrizRequest request) {
        validarMatrizAtiva(request.getCursoId(), request.getAtivo(), id);
        Matriz matriz = buscar(id);
        preencher(matriz, request);
        return matrizRepository.save(matriz);
    }

    public Matriz inativar(Long id) {
        Matriz matriz = buscar(id);
        matriz.setAtivo(false);
        return matrizRepository.save(matriz);
    }

    private void validarMatrizAtiva(Long cursoId, Boolean ativo, Long idAtual) {
        if (Boolean.TRUE.equals(ativo)) {
            boolean existe = idAtual == null
                    ? matrizRepository.existsByCursoIdAndAtivoTrue(cursoId)
                    : matrizRepository.existsByCursoIdAndAtivoTrueAndIdNot(cursoId, idAtual);
            if (existe) {
                throw new BusinessRuleException("Não podem existir duas matrizes ativas para o mesmo curso");
            }
        }
    }

    private void preencher(Matriz matriz, MatrizRequest request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com id " + request.getCursoId()));
        List<Disciplina> disciplinas = disciplinaRepository.findAllById(request.getDisciplinaIds());
        matriz.setNome(request.getNome());
        matriz.setDescricao(request.getDescricao());
        matriz.setCurso(curso);
        matriz.setDisciplinas(disciplinas);
        matriz.setDataCadastro(request.getDataCadastro() != null ? request.getDataCadastro() : LocalDate.now());
        matriz.setAtivo(request.getAtivo() == null || request.getAtivo());
    }
}
