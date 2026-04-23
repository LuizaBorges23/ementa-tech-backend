package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.ProgramaDisciplinaRequest;
import com.example.disciplinas.educacao.dto.ProgramaDisciplinaResponse;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.service.ProgramaDisciplinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/programas-disciplinas")
public class AdminProgramaDisciplinaController {

    private final ProgramaDisciplinaService service;

    public AdminProgramaDisciplinaController(ProgramaDisciplinaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProgramaDisciplinaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDisciplinaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ProgramaDisciplina> salvar(@Valid @RequestBody ProgramaDisciplinaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaDisciplina> atualizar(@PathVariable Long id, @Valid @RequestBody ProgramaDisciplinaRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<ProgramaDisciplina> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
