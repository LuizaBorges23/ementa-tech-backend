package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.CursoRequest;
import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cursos")
public class AdminCursoController {

    private final CursoService service;

    public AdminCursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Curso> salvar(@Valid @RequestBody CursoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Curso> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
