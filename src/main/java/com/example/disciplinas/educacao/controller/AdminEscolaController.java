package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.EscolaRequest;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/escolas")
public class AdminEscolaController {

    private final EscolaService service;

    public AdminEscolaController(EscolaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Escola>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escola> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Escola> salvar(@Valid @RequestBody EscolaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escola> atualizar(@PathVariable Long id, @Valid @RequestBody EscolaRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Escola> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}
