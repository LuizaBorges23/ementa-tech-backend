package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.entity.*;
import com.example.disciplinas.educacao.enums.RoleName;
import com.example.disciplinas.educacao.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final InstituicaoEnsinoSuperiorRepository iesRepository;
    private final EscolaRepository escolaRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProgramaDisciplinaRepository programaDisciplinaRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(InstituicaoEnsinoSuperiorRepository iesRepository,
                          EscolaRepository escolaRepository,
                          ProfessorRepository professorRepository,
                          UsuarioRepository usuarioRepository,
                          CursoRepository cursoRepository,
                          DisciplinaRepository disciplinaRepository,
                          ProgramaDisciplinaRepository programaDisciplinaRepository,
                          PasswordEncoder passwordEncoder) {
        this.iesRepository = iesRepository;
        this.escolaRepository = escolaRepository;
        this.professorRepository = professorRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.programaDisciplinaRepository = programaDisciplinaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (iesRepository.count() > 0) {
            return;
        }

        InstituicaoEnsinoSuperior ies = new InstituicaoEnsinoSuperior();
        ies.setNome("Universidade Católica do Salvador");
        ies.setEndereco("Av. Prof. Pinto de Aguiar, Salvador - BA");
        ies.setTelefone("(71) 3206-0000");
        ies = iesRepository.save(ies);

        Escola escola1 = criarEscola("Escola de Educação, Cultura e Humanidades", "Coordenação Escola 1", ies);
        Escola escola2 = criarEscola("Escola de Ciências Sociais e Aplicadas", "Coordenação Escola 2", ies);
        criarEscola("Escola de Engenharias e Ciências Tecnológicas", "Coordenação Escola 3", ies);
        criarEscola("Escola de Ciências Naturais e da Saúde", "Coordenação Escola 4", ies);

        Professor professor = new Professor();
        professor.setMatricula("2026001");
        professor.setNomeCompleto("Professor Exemplo");
        professor.setEmail("professor@ementatech.com");
        professor.setTelefone("71999999999");
        professor.setAtivo(true);
        professor.setEscola(escola2);
        professor = professorRepository.save(professor);

        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(RoleName.ROLE_ADMIN);
        admin.setEnabled(true);
        usuarioRepository.save(admin);

        Usuario userProfessor = new Usuario();
        userProfessor.setUsername("professor1");
        userProfessor.setPassword(passwordEncoder.encode("prof123"));
        userProfessor.setRole(RoleName.ROLE_PROFESSOR);
        userProfessor.setEnabled(true);
        userProfessor.setProfessor(professor);
        usuarioRepository.save(userProfessor);

        Curso curso = new Curso();
        curso.setSigla("ADS");
        curso.setDescricao("Análise e Desenvolvimento de Sistemas");
        curso.setDataCadastro(LocalDate.now());
        curso.setAtivo(true);
        curso.setEscola(escola2);
        curso.setCoordenadorCurso(professor);
        curso = cursoRepository.save(curso);

        Disciplina disciplina = new Disciplina();
        disciplina.setSigla("DS101");
        disciplina.setDescricao("Desenvolvimento de Sistemas");
        disciplina.setCargaHoraria(60);
        disciplina.setDataCadastro(LocalDate.now());
        disciplina.setAtivo(true);
        disciplina.setEscola(escola2);
        disciplina.setProfessor(professor);
        List<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso);
        disciplina.setCursos(listaCursos);
        disciplina = disciplinaRepository.save(disciplina);

        ProgramaDisciplina programa = new ProgramaDisciplina();
        programa.setDisciplina(disciplina);
        programa.setSemestre(1);
        programa.setEmenta("Ementa inicial para demonstração do sistema.");
        programa.setCompetenciasHabilidades("Competências e habilidades iniciais.");
        programa.setConteudoProgramatico("Unidade 1, Unidade 2, Unidade 3.");
        programa.setMetodologia("Aulas expositivas e práticas.");
        programa.setProcessoAvaliacao("Avaliações parciais e trabalho final.");
        programa.setDataCadastro(LocalDate.now());
        programa.setAtivo(true);
        programaDisciplinaRepository.save(programa);
    }

    private Escola criarEscola(String nome, String coordenador, InstituicaoEnsinoSuperior ies) {
        Escola escola = new Escola();
        escola.setNome(nome);
        escola.setCoordenador(coordenador);
        escola.setAtivo(true);
        escola.setIes(ies);
        return escolaRepository.save(escola);
    }
}
