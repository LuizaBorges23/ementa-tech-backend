package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.FormacaoProfessorRequest;
import com.example.disciplinas.educacao.dto.ProfessorRequest;
import com.example.disciplinas.educacao.entity.FormacaoProfessor;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/professores")
public class AdminProfessorController {

    private final ProfessorService service;

    public AdminProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Professor> salvar(@Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Professor> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Professor> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ativar(id));
    }

    @PostMapping("/{id}/formacoes")
    public ResponseEntity<FormacaoProfessor> adicionarFormacao(@PathVariable Long id, @Valid @RequestBody FormacaoProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarFormacaoAoProfessor(id, request));
    }
}
