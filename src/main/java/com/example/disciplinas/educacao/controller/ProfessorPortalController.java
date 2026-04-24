package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.BibliografiaRequest;
import com.example.disciplinas.educacao.dto.FormacaoProfessorRequest;
import com.example.disciplinas.educacao.dto.ProfessorPortalResponse;
import com.example.disciplinas.educacao.dto.ProgramaDisciplinaResponse;
import com.example.disciplinas.educacao.entity.BibliografiaBasica;
import com.example.disciplinas.educacao.entity.BibliografiaComplementar;
import com.example.disciplinas.educacao.entity.FormacaoProfessor;
import com.example.disciplinas.educacao.service.BibliografiaService;
import com.example.disciplinas.educacao.service.ProfessorService;
import com.example.disciplinas.educacao.service.ProgramaDisciplinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorPortalController {

    private final ProfessorService professorService;
    private final ProgramaDisciplinaService programaDisciplinaService;
    private final BibliografiaService bibliografiaService;

    public ProfessorPortalController(ProfessorService professorService,
                                     ProgramaDisciplinaService programaDisciplinaService,
                                     BibliografiaService bibliografiaService) {
        this.professorService = professorService;
        this.programaDisciplinaService = programaDisciplinaService;
        this.bibliografiaService = bibliografiaService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfessorPortalResponse> meuCadastro(Authentication authentication) {
        return ResponseEntity.ok(professorService.buscarPortalPorUsername(authentication.getName()));
    }

    @PostMapping("/me/formacoes")
    public ResponseEntity<FormacaoProfessor> adicionarFormacao(Authentication authentication,
                                                               @Valid @RequestBody FormacaoProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(professorService.adicionarFormacaoDoProfessor(authentication.getName(), request));
    }

    @GetMapping("/programas")
    public ResponseEntity<List<ProgramaDisciplinaResponse>> meusProgramas(Authentication authentication) {
        return ResponseEntity.ok(programaDisciplinaService.listarDoProfessorDetalhado(authentication.getName(), professorService));
    }

    @GetMapping("/programas/incompletos")
    public ResponseEntity<List<ProgramaDisciplinaResponse>> programasIncompletos(Authentication authentication) {
        return ResponseEntity.ok(programaDisciplinaService.listarIncompletosDoProfessorDetalhado(authentication.getName(), professorService));
    }

    @PostMapping("/programas/{programaId}/bibliografias/basicas")
    public ResponseEntity<BibliografiaBasica> adicionarBasica(Authentication authentication,
                                                              @PathVariable Long programaId,
                                                              @Valid @RequestBody BibliografiaRequest request) {
        programaDisciplinaService.validarProgramaDoProfessor(programaId, authentication.getName(), professorService);
        return ResponseEntity.status(HttpStatus.CREATED).body(bibliografiaService.adicionarBasica(programaId, request));
    }

    @PostMapping("/programas/{programaId}/bibliografias/complementares")
    public ResponseEntity<BibliografiaComplementar> adicionarComplementar(Authentication authentication,
                                                                          @PathVariable Long programaId,
                                                                          @Valid @RequestBody BibliografiaRequest request) {
        programaDisciplinaService.validarProgramaDoProfessor(programaId, authentication.getName(), professorService);
        return ResponseEntity.status(HttpStatus.CREATED).body(bibliografiaService.adicionarComplementar(programaId, request));
    }
}
