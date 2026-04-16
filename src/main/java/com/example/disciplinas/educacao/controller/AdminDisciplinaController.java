package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.DisciplinaRequest;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/disciplinas")
public class AdminDisciplinaController {

    private final DisciplinaService service;

    public AdminDisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Disciplina> salvar(@Valid @RequestBody DisciplinaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long id, @Valid @RequestBody DisciplinaRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Disciplina> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
