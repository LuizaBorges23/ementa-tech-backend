package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.service.CursoService;
import com.example.disciplinas.educacao.service.EscolaService;
import com.example.disciplinas.educacao.service.ProfessorService;
import com.example.disciplinas.educacao.service.ProgramaDisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final EscolaService escolaService;
    private final ProfessorService professorService;
    private final CursoService cursoService;
    private final ProgramaDisciplinaService programaDisciplinaService;

    public RelatorioController(EscolaService escolaService,
                               ProfessorService professorService,
                               CursoService cursoService,
                               ProgramaDisciplinaService programaDisciplinaService) {
        this.escolaService = escolaService;
        this.professorService = professorService;
        this.cursoService = cursoService;
        this.programaDisciplinaService = programaDisciplinaService;
    }

    @GetMapping("/escolas")
    public ResponseEntity<List<Escola>> escolas() {
        return ResponseEntity.ok(escolaService.listar());
    }

    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> professores() {
        return ResponseEntity.ok(professorService.listar());
    }

    @GetMapping("/cursos-matrizes")
    public ResponseEntity<List<Curso>> cursosComMatrizes() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/programas")
    public ResponseEntity<List<ProgramaDisciplina>> programas() {
        return ResponseEntity.ok(programaDisciplinaService.listar());
    }

    @GetMapping("/programas/{id}")
    public ResponseEntity<ProgramaDisciplina> programaDetalhado(@PathVariable Long id) {
        return ResponseEntity.ok(programaDisciplinaService.buscar(id));
    }

    @GetMapping("/programas/incompletos")
    public ResponseEntity<List<ProgramaDisciplina>> programasIncompletos() {
        return ResponseEntity.ok(programaDisciplinaService.listarIncompletos());
    }
}
