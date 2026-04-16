package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.BibliografiaRequest;
import com.example.disciplinas.educacao.entity.BibliografiaBasica;
import com.example.disciplinas.educacao.entity.BibliografiaComplementar;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.enums.LocalizacaoBibliografia;
import com.example.disciplinas.educacao.exception.BusinessRuleException;
import com.example.disciplinas.educacao.repository.BibliografiaBasicaRepository;
import com.example.disciplinas.educacao.repository.BibliografiaComplementarRepository;
import org.springframework.stereotype.Service;

@Service
public class BibliografiaService {

    private final ProgramaDisciplinaService programaDisciplinaService;
    private final BibliografiaBasicaRepository bibliografiaBasicaRepository;
    private final BibliografiaComplementarRepository bibliografiaComplementarRepository;

    public BibliografiaService(ProgramaDisciplinaService programaDisciplinaService,
                               BibliografiaBasicaRepository bibliografiaBasicaRepository,
                               BibliografiaComplementarRepository bibliografiaComplementarRepository) {
        this.programaDisciplinaService = programaDisciplinaService;
        this.bibliografiaBasicaRepository = bibliografiaBasicaRepository;
        this.bibliografiaComplementarRepository = bibliografiaComplementarRepository;
    }

    public BibliografiaBasica adicionarBasica(Long programaId, BibliografiaRequest request) {
        ProgramaDisciplina programa = programaDisciplinaService.buscar(programaId);
        long quantidade = bibliografiaBasicaRepository.countByProgramaDisciplinaId(programaId);
        if (quantidade >= 3) {
            throw new BusinessRuleException("O programa já possui as 3 bibliografias básicas exigidas");
        }
        validarLocalizacao(request);
        BibliografiaBasica bibliografia = new BibliografiaBasica();
        bibliografia.setProgramaDisciplina(programa);
        bibliografia.setTitulo(request.getTitulo());
        bibliografia.setAutores(request.getAutores());
        bibliografia.setEditora(request.getEditora());
        bibliografia.setIsbn(request.getIsbn());
        bibliografia.setAnoPublicacao(request.getAnoPublicacao());
        bibliografia.setLocalizacao(request.getLocalizacao());
        bibliografia.setLinkLivro(request.getLinkLivro());
        bibliografia.setPosicaoEstante(request.getPosicaoEstante());
        return bibliografiaBasicaRepository.save(bibliografia);
    }

    public BibliografiaComplementar adicionarComplementar(Long programaId, BibliografiaRequest request) {
        ProgramaDisciplina programa = programaDisciplinaService.buscar(programaId);
        long quantidade = bibliografiaComplementarRepository.countByProgramaDisciplinaId(programaId);
        if (quantidade >= 5) {
            throw new BusinessRuleException("O programa já possui as 5 bibliografias complementares exigidas");
        }
        validarLocalizacao(request);
        BibliografiaComplementar bibliografia = new BibliografiaComplementar();
        bibliografia.setProgramaDisciplina(programa);
        bibliografia.setTitulo(request.getTitulo());
        bibliografia.setAutores(request.getAutores());
        bibliografia.setEditora(request.getEditora());
        bibliografia.setIsbn(request.getIsbn());
        bibliografia.setAnoPublicacao(request.getAnoPublicacao());
        bibliografia.setLocalizacao(request.getLocalizacao());
        bibliografia.setLinkLivro(request.getLinkLivro());
        bibliografia.setPosicaoEstante(request.getPosicaoEstante());
        return bibliografiaComplementarRepository.save(bibliografia);
    }

    private void validarLocalizacao(BibliografiaRequest request) {
        if (request.getLocalizacao() == LocalizacaoBibliografia.DIGITAL && (request.getLinkLivro() == null || request.getLinkLivro().isBlank())) {
            throw new BusinessRuleException("Quando a localização for DIGITAL, o link do livro é obrigatório");
        }
        if (request.getLocalizacao() == LocalizacaoBibliografia.FISICO && (request.getPosicaoEstante() == null || request.getPosicaoEstante().isBlank())) {
            throw new BusinessRuleException("Quando a localização for FISICO, a posição na estante é obrigatória");
        }
    }
}
