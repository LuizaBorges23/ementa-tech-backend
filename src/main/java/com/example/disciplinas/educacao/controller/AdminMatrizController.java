package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.MatrizRequest;
import com.example.disciplinas.educacao.entity.Matriz;
import com.example.disciplinas.educacao.service.MatrizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/matrizes")
public class AdminMatrizController {

    private final MatrizService service;

    public AdminMatrizController(MatrizService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Matriz>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matriz> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Matriz> salvar(@Valid @RequestBody MatrizRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matriz> atualizar(@PathVariable Long id, @Valid @RequestBody MatrizRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Matriz> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
