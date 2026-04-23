package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.entity.BibliografiaBasica;
import com.example.disciplinas.educacao.entity.BibliografiaComplementar;
import com.example.disciplinas.educacao.entity.Curso;
import com.example.disciplinas.educacao.entity.Disciplina;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.InstituicaoEnsinoSuperior;
import com.example.disciplinas.educacao.entity.Matriz;
import com.example.disciplinas.educacao.entity.Professor;
import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import com.example.disciplinas.educacao.entity.Usuario;
import com.example.disciplinas.educacao.enums.LocalizacaoBibliografia;
import com.example.disciplinas.educacao.enums.RoleName;
import com.example.disciplinas.educacao.repository.BibliografiaBasicaRepository;
import com.example.disciplinas.educacao.repository.BibliografiaComplementarRepository;
import com.example.disciplinas.educacao.repository.CursoRepository;
import com.example.disciplinas.educacao.repository.DisciplinaRepository;
import com.example.disciplinas.educacao.repository.EscolaRepository;
import com.example.disciplinas.educacao.repository.InstituicaoEnsinoSuperiorRepository;
import com.example.disciplinas.educacao.repository.MatrizRepository;
import com.example.disciplinas.educacao.repository.ProfessorRepository;
import com.example.disciplinas.educacao.repository.ProgramaDisciplinaRepository;
import com.example.disciplinas.educacao.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final String[] AUTORES = {
            "Ana Lucia Carvalho",
            "Bruno Almeida Santos",
            "Carla Menezes Lima",
            "Daniel Rodrigues Costa",
            "Eduardo Matos Vieira",
            "Fernanda Pires Rocha",
            "Gustavo Ribeiro Souza",
            "Helena Castro Almeida",
            "Igor Nascimento Silva",
            "Juliana Andrade Moreira",
            "Leandro Borges Teixeira",
            "Mariana Farias Gomes"
    };

    private final InstituicaoEnsinoSuperiorRepository iesRepository;
    private final EscolaRepository escolaRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProgramaDisciplinaRepository programaDisciplinaRepository;
    private final MatrizRepository matrizRepository;
    private final BibliografiaBasicaRepository bibliografiaBasicaRepository;
    private final BibliografiaComplementarRepository bibliografiaComplementarRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(InstituicaoEnsinoSuperiorRepository iesRepository,
                          EscolaRepository escolaRepository,
                          ProfessorRepository professorRepository,
                          UsuarioRepository usuarioRepository,
                          CursoRepository cursoRepository,
                          DisciplinaRepository disciplinaRepository,
                          ProgramaDisciplinaRepository programaDisciplinaRepository,
                          MatrizRepository matrizRepository,
                          BibliografiaBasicaRepository bibliografiaBasicaRepository,
                          BibliografiaComplementarRepository bibliografiaComplementarRepository,
                          PasswordEncoder passwordEncoder) {
        this.iesRepository = iesRepository;
        this.escolaRepository = escolaRepository;
        this.professorRepository = professorRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.programaDisciplinaRepository = programaDisciplinaRepository;
        this.matrizRepository = matrizRepository;
        this.bibliografiaBasicaRepository = bibliografiaBasicaRepository;
        this.bibliografiaComplementarRepository = bibliografiaComplementarRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (iesRepository.count() > 0) {
            return;
        }

        InstituicaoEnsinoSuperior ies = criarInstituicao();

        Escola escolaMusica = criarEscola("Escola de Educacao, Cultura e Humanidades", "Coordenacao Escola de Musica", ies);
        Escola escolaDireito = criarEscola("Escola de Ciencias Sociais e Aplicadas", "Coordenacao Escola de Direito", ies);
        Escola escolaEngenharia = criarEscola("Escola de Engenharias e Ciencias Tecnologica", "Coordenacao Escola de Engenharia", ies);
        Escola escolaBiomedicina = criarEscola("Escola de Ciencias Naturais e da Saude", "Coordenacao Escola de Biomedicina", ies);

        Professor professorMusica = criarProfessor(
                "2026001",
                "Prof. Carlos Andre da Cruz Leandro",
                "carlos.leandro@ementatech.com",
                "71999990001",
                escolaMusica
        );
        Professor professorDireito = criarProfessor(
                "2026002",
                "Profa. Joelma Ferreira Silva Primo Pacheco",
                "joelma.pacheco@ementatech.com",
                "71999990002",
                escolaDireito
        );
        Professor professorEngenharia = criarProfessor(
                "2026003",
                "Prof. Osvaldo Requiao Melo",
                "osvaldo.melo@ementatech.com",
                "71999990003",
                escolaEngenharia
        );
        Professor professorBiomedicina = criarProfessor(
                "2026004",
                "Dr. Orivaldo Pereira Ramos Paranainfa",
                "orivaldo.paranainfa@ementatech.com",
                "71999990004",
                escolaBiomedicina
        );

        criarUsuarioAdmin();
        criarUsuarioProfessor("professor1", "prof123", professorEngenharia);

        Curso cursoEngenharia = criarCurso("BES", "Engenharia de Software", data(3, 1), escolaEngenharia, professorEngenharia);
        Curso cursoBiomedicina = criarCurso("BME", "Biomedicina", data(3, 1), escolaBiomedicina, professorBiomedicina);
        Curso cursoMusica = criarCurso("MUS", "Musica", data(3, 1), escolaMusica, professorMusica);
        Curso cursoDireito = criarCurso("DIR", "Direito", data(3, 1), escolaDireito, professorDireito);

        List<DisciplinaSeed> gradeCompleta = new ArrayList<>();
        adicionarGradeEngenharia(gradeCompleta, escolaEngenharia, professorEngenharia, cursoEngenharia);
        adicionarGradeDireito(gradeCompleta, escolaDireito, professorDireito, cursoDireito);
        adicionarGradeBiomedicina(gradeCompleta, escolaBiomedicina, professorBiomedicina, cursoBiomedicina);
        adicionarGradeMusica(gradeCompleta, escolaMusica, professorMusica, cursoMusica);

        Map<String, Disciplina> disciplinasPorSigla = new LinkedHashMap<>();
        for (DisciplinaSeed seed : gradeCompleta) {
            disciplinasPorSigla.put(seed.sigla(), criarDisciplina(seed));
        }

        for (DisciplinaSeed seed : gradeCompleta) {
            ProgramaDisciplina programa = criarProgramaCompleto(seed, disciplinasPorSigla);
            criarBibliografiasPadrao(programa, seed);
        }

        criarMatrizCompleta(cursoEngenharia, gradeCompleta, disciplinasPorSigla);
        criarMatrizCompleta(cursoDireito, gradeCompleta, disciplinasPorSigla);
        criarMatrizCompleta(cursoBiomedicina, gradeCompleta, disciplinasPorSigla);
        criarMatrizCompleta(cursoMusica, gradeCompleta, disciplinasPorSigla);
    }

    private InstituicaoEnsinoSuperior criarInstituicao() {
        InstituicaoEnsinoSuperior ies = new InstituicaoEnsinoSuperior();
        ies.setNome("Universidade Catolica do Salvador");
        ies.setEndereco("Av. Prof. Pinto de Aguiar, Salvador - BA");
        ies.setTelefone("(71) 3206-0000");
        return iesRepository.save(ies);
    }

    private Escola criarEscola(String nome, String coordenador, InstituicaoEnsinoSuperior ies) {
        Escola escola = new Escola();
        escola.setNome(nome);
        escola.setCoordenador(coordenador);
        escola.setAtivo(true);
        escola.setIes(ies);
        return escolaRepository.save(escola);
    }

    private Professor criarProfessor(String matricula, String nomeCompleto, String email, String telefone, Escola escola) {
        Professor professor = new Professor();
        professor.setMatricula(matricula);
        professor.setNomeCompleto(nomeCompleto);
        professor.setEmail(email);
        professor.setTelefone(telefone);
        professor.setAtivo(true);
        professor.setEscola(escola);
        return professorRepository.save(professor);
    }

    private void criarUsuarioAdmin() {
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(RoleName.ROLE_ADMIN);
        admin.setEnabled(true);
        usuarioRepository.save(admin);
    }

    private void criarUsuarioProfessor(String username, String password, Professor professor) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRole(RoleName.ROLE_PROFESSOR);
        usuario.setEnabled(true);
        usuario.setProfessor(professor);
        usuarioRepository.save(usuario);
    }

    private Curso criarCurso(String sigla, String descricao, LocalDate dataCadastro, Escola escola, Professor coordenador) {
        Curso curso = new Curso();
        curso.setSigla(sigla);
        curso.setDescricao(descricao);
        curso.setDataCadastro(dataCadastro);
        curso.setAtivo(true);
        curso.setEscola(escola);
        curso.setCoordenadorCurso(coordenador);
        return cursoRepository.save(curso);
    }

    private Disciplina criarDisciplina(DisciplinaSeed seed) {
        Disciplina disciplina = new Disciplina();
        disciplina.setSigla(seed.sigla());
        disciplina.setDescricao(seed.descricao());
        disciplina.setCargaHoraria(seed.cargaHoraria());
        disciplina.setDataCadastro(seed.dataCadastro());
        disciplina.setAtivo(true);
        disciplina.setEscola(seed.escola());
        disciplina.setProfessor(seed.professor());
        disciplina.setCursos(new ArrayList<>(seed.cursos()));
        return disciplinaRepository.save(disciplina);
    }

    private ProgramaDisciplina criarProgramaCompleto(DisciplinaSeed seed, Map<String, Disciplina> disciplinasPorSigla) {
        Disciplina disciplina = disciplinasPorSigla.get(seed.sigla());
        ProgramaDisciplina programa = new ProgramaDisciplina();
        programa.setDisciplina(disciplina);
        programa.setSemestre(seed.semestre());
        programa.setEmenta(gerarEmenta(seed));
        programa.setCompetenciasHabilidades(gerarCompetencias(seed));
        programa.setConteudoProgramatico(gerarConteudo(seed));
        programa.setMetodologia(gerarMetodologia(seed));
        programa.setProcessoAvaliacao(gerarAvaliacao(seed));
        programa.setDataCadastro(seed.dataCadastro());
        programa.setAtivo(true);
        programa.setPrerequisitos(seed.prerequisitosSiglas().stream()
                .map(disciplinasPorSigla::get)
                .filter(item -> item != null)
                .toList());
        return programaDisciplinaRepository.save(programa);
    }

    private void criarBibliografiasPadrao(ProgramaDisciplina programa, DisciplinaSeed seed) {
        for (int ordem = 1; ordem <= 3; ordem++) {
            bibliografiaBasicaRepository.save(novaBibliografiaBasica(programa, seed, ordem));
        }

        for (int ordem = 1; ordem <= 5; ordem++) {
            bibliografiaComplementarRepository.save(novaBibliografiaComplementar(programa, seed, ordem));
        }
    }

    private BibliografiaBasica novaBibliografiaBasica(ProgramaDisciplina programa, DisciplinaSeed seed, int ordem) {
        BibliografiaBasica bibliografia = new BibliografiaBasica();
        bibliografia.setProgramaDisciplina(programa);
        bibliografia.setTitulo(tituloBibliografia(seed, ordem, true));
        bibliografia.setAutores(autorPara(seed.sigla(), ordem));
        bibliografia.setEditora("Editora EmentaTech");
        bibliografia.setIsbn(gerarIsbn(seed.sigla(), ordem, true));
        bibliografia.setAnoPublicacao(2018 + ordem);
        configurarLocalizacaoBasica(bibliografia, seed, ordem);
        return bibliografia;
    }

    private BibliografiaComplementar novaBibliografiaComplementar(ProgramaDisciplina programa, DisciplinaSeed seed, int ordem) {
        BibliografiaComplementar bibliografia = new BibliografiaComplementar();
        bibliografia.setProgramaDisciplina(programa);
        bibliografia.setTitulo(tituloBibliografia(seed, ordem, false));
        bibliografia.setAutores(autorPara(seed.sigla(), ordem + 3));
        bibliografia.setEditora("Colecao Academica UCSAL");
        bibliografia.setIsbn(gerarIsbn(seed.sigla(), ordem, false));
        bibliografia.setAnoPublicacao(2014 + ordem);
        configurarLocalizacaoComplementar(bibliografia, seed, ordem);
        return bibliografia;
    }

    private void configurarLocalizacaoBasica(BibliografiaBasica bibliografia, DisciplinaSeed seed, int ordem) {
        if (ordem % 2 == 0) {
            bibliografia.setLocalizacao(LocalizacaoBibliografia.DIGITAL);
            bibliografia.setLinkLivro(linkBiblioteca(seed.sigla(), ordem, "basica"));
        } else {
            bibliografia.setLocalizacao(LocalizacaoBibliografia.FISICO);
            bibliografia.setPosicaoEstante(posicaoEstante(seed.sigla(), ordem, "B"));
        }
    }

    private void configurarLocalizacaoComplementar(BibliografiaComplementar bibliografia, DisciplinaSeed seed, int ordem) {
        if (ordem % 2 == 1) {
            bibliografia.setLocalizacao(LocalizacaoBibliografia.DIGITAL);
            bibliografia.setLinkLivro(linkBiblioteca(seed.sigla(), ordem, "complementar"));
        } else {
            bibliografia.setLocalizacao(LocalizacaoBibliografia.FISICO);
            bibliografia.setPosicaoEstante(posicaoEstante(seed.sigla(), ordem, "C"));
        }
    }

    private void criarMatrizCompleta(Curso curso, List<DisciplinaSeed> gradeCompleta, Map<String, Disciplina> disciplinasPorSigla) {
        List<Disciplina> disciplinasDaMatriz = gradeCompleta.stream()
                .filter(seed -> seed.cursos().contains(curso))
                .sorted(Comparator.comparingInt(DisciplinaSeed::semestre).thenComparing(DisciplinaSeed::sigla))
                .map(seed -> disciplinasPorSigla.get(seed.sigla()))
                .toList();

        Matriz matriz = new Matriz();
        matriz.setNome("Matriz curricular 2026 - " + curso.getDescricao());
        matriz.setDescricao("Grade completa organizada por semestres para o curso de " + curso.getDescricao() + ".");
        matriz.setCurso(curso);
        matriz.setDisciplinas(disciplinasDaMatriz);
        matriz.setDataCadastro(curso.getDataCadastro());
        matriz.setAtivo(true);
        matrizRepository.save(matriz);
    }

    private String gerarEmenta(DisciplinaSeed seed) {
        if (isTrabalhoFinal(seed)) {
            return "Orienta o planejamento, a execucao e a apresentacao de um produto academico ou profissional relacionado a "
                    + seed.eixoTematico().toLowerCase(Locale.ROOT)
                    + ", articulando pesquisa, pratica e comunicacao de resultados.";
        }

        if (isEstagio(seed)) {
            return "Promove a integracao entre conhecimentos teoricos e vivencias supervisionadas, com foco em "
                    + seed.eixoTematico().toLowerCase(Locale.ROOT)
                    + " e nas rotinas profissionais do curso.";
        }

        return "Aborda " + seed.descricao().toLowerCase(Locale.ROOT)
                + " com foco em " + seed.eixoTematico().toLowerCase(Locale.ROOT)
                + ", articulando fundamentos conceituais, aplicacoes praticas e resolucao de problemas da formacao em "
                + nomeCursoPrincipal(seed) + ".";
    }

    private String gerarCompetencias(DisciplinaSeed seed) {
        return "Desenvolver autonomia para analisar " + seed.topicos().get(0).toLowerCase(Locale.ROOT)
                + ", aplicar conhecimentos relacionados a " + seed.topicos().get(1).toLowerCase(Locale.ROOT)
                + " e produzir entregas consistentes com as exigencias academicas e profissionais de "
                + nomeCursoPrincipal(seed) + ".";
    }

    private String gerarConteudo(DisciplinaSeed seed) {
        return String.join("; ", seed.topicos());
    }

    private String gerarMetodologia(DisciplinaSeed seed) {
        String prefixo = prefixoCurso(seed.sigla());
        return switch (prefixo) {
            case "BES" -> "Aulas dialogadas, laboratorios de desenvolvimento, resolucao de desafios, modelagem colaborativa e projetos incrementais com acompanhamento docente.";
            case "DIR" -> "Aulas expositivas, leitura orientada de legislacao e jurisprudencia, estudos de caso, producao de pecas e debates mediados.";
            case "BME" -> "Aulas teoricas, praticas laboratoriais, analise de laudos, estudos dirigidos e discussao de casos clinicos supervisionados.";
            case "MUS" -> "Aulas em estudio e laboratorio, oficinas de criacao, ensaios orientados, escuta guiada e apresentacoes comentadas.";
            default -> "Aulas expositivas, estudos dirigidos e atividades praticas supervisionadas.";
        };
    }

    private String gerarAvaliacao(DisciplinaSeed seed) {
        if (isTrabalhoFinal(seed)) {
            return "Avaliacao por projeto final, acompanhamento das etapas de desenvolvimento, defesa oral e entrega documentada.";
        }

        if (isEstagio(seed)) {
            return "Avaliacao por relatorios reflexivos, desempenho nas atividades supervisionadas, frequencia e apresentacao final da experiencia profissional.";
        }

        String prefixo = prefixoCurso(seed.sigla());
        return switch (prefixo) {
            case "BES" -> "Avaliacao continua com atividades praticas, desafios aplicados, verificacoes individuais e entrega de projeto integrador.";
            case "DIR" -> "Avaliacao por provas discursivas, estudos de caso, seminarios tematicos e producao tecnico-juridica orientada.";
            case "BME" -> "Avaliacao por provas teoricas, relatorios de pratica, analise de casos e atividades de laboratorio.";
            case "MUS" -> "Avaliacao por performances orientadas, registros de processo, exercicios tecnicos e producao artistica final.";
            default -> "Avaliacao continua com exercicios, estudos dirigidos e trabalho final.";
        };
    }

    private boolean isTrabalhoFinal(DisciplinaSeed seed) {
        String descricao = seed.descricao().toLowerCase(Locale.ROOT);
        return descricao.contains("tcc") || descricao.contains("projeto artistico");
    }

    private boolean isEstagio(DisciplinaSeed seed) {
        return seed.descricao().toLowerCase(Locale.ROOT).contains("estagio");
    }

    private String nomeCursoPrincipal(DisciplinaSeed seed) {
        return seed.cursos().isEmpty() ? "formacao superior" : seed.cursos().get(0).getDescricao();
    }

    private String prefixoCurso(String sigla) {
        return sigla.length() >= 3 ? sigla.substring(0, 3) : sigla;
    }

    private String tituloBibliografia(DisciplinaSeed seed, int ordem, boolean basica) {
        String descricao = seed.descricao();
        if (basica) {
            return switch (ordem) {
                case 1 -> "Fundamentos de " + descricao;
                case 2 -> "Praticas orientadas de " + descricao;
                default -> seed.eixoTematico() + " aplicado a " + nomeCursoPrincipal(seed);
            };
        }

        return switch (ordem) {
            case 1 -> "Estudos contemporaneos em " + descricao;
            case 2 -> "Casos e projetos em " + descricao;
            case 3 -> "Topicos avancados de " + seed.eixoTematico();
            case 4 -> "Leituras complementares de " + descricao;
            default -> "Panorama profissional de " + nomeCursoPrincipal(seed);
        };
    }

    private String autorPara(String sigla, int ordem) {
        int indice = Math.floorMod((sigla + ordem).hashCode(), AUTORES.length);
        return AUTORES[indice];
    }

    private String gerarIsbn(String sigla, int ordem, boolean basica) {
        int base = Math.abs((sigla + ordem + (basica ? "B" : "C")).hashCode());
        String numero = String.valueOf(978000000000L + (base % 1_000_000_000L));
        return numero.substring(0, 3) + "-" + numero.substring(3, 4) + "-" + numero.substring(4, 7) + "-" + numero.substring(7, 12) + "-" + numero.substring(12);
    }

    private String linkBiblioteca(String sigla, int ordem, String tipo) {
        return "https://biblioteca.ementatech.edu.br/acervo/" + tipo + "/" + slug(sigla) + "-" + ordem;
    }

    private String posicaoEstante(String sigla, int ordem, String tipo) {
        return tipo + "-" + sigla + "-" + ordem;
    }

    private String slug(String valor) {
        return valor.toLowerCase(Locale.ROOT).replace(" ", "-");
    }

    private LocalDate data(int mes, int dia) {
        return LocalDate.of(2026, mes, dia);
    }

    private DisciplinaSeed seed(String sigla,
                                String descricao,
                                int cargaHoraria,
                                LocalDate dataCadastro,
                                int semestre,
                                String eixoTematico,
                                List<String> topicos,
                                Escola escola,
                                Professor professor,
                                Curso curso,
                                String... prerequisitos) {
        return new DisciplinaSeed(
                sigla,
                descricao,
                cargaHoraria,
                dataCadastro,
                semestre,
                eixoTematico,
                topicos,
                escola,
                professor,
                List.of(curso),
                List.of(prerequisitos)
        );
    }

    private void adicionarGradeEngenharia(List<DisciplinaSeed> grade, Escola escola, Professor professor, Curso curso) {
        grade.add(seed("BES001", "Logica de Programacao", 90, data(3, 15), 1, "Fundamentos da computacao",
                List.of("Algoritmos e logica proposicional", "Variaveis, operadores e expressoes", "Estruturas condicionais", "Estruturas de repeticao", "Modularizacao e funcoes"),
                escola, professor, curso));
        grade.add(seed("BES002", "Fundamentos de Computacao", 60, data(3, 4), 1, "Bases computacionais",
                List.of("Historia da computacao", "Representacao de dados", "Arquitetura basica de computadores", "Sistemas operacionais introdutorios", "Pensamento computacional"),
                escola, professor, curso));
        grade.add(seed("BES003", "Programacao Orientada a Objetos", 80, data(3, 9), 2, "Desenvolvimento de software",
                List.of("Classes, objetos e encapsulamento", "Heranca e polimorfismo", "Colecoes e tratamento de excecoes", "Boas praticas de programacao", "Testes unitarios introdutorios"),
                escola, professor, curso, "BES001"));
        grade.add(seed("BES004", "Banco de Dados I", 60, data(3, 11), 2, "Dados e persistencia",
                List.of("Modelagem entidade-relacionamento", "Normalizacao de dados", "SQL basico", "Consultas e filtros", "Integridade e relacionamento entre tabelas"),
                escola, professor, curso, "BES001"));
        grade.add(seed("BES005", "Estrutura de Dados", 80, data(3, 18), 3, "Algoritmos e desempenho",
                List.of("Analise de complexidade", "Listas e pilhas", "Filas e arvores", "Recursao", "Ordenacao e busca"),
                escola, professor, curso, "BES003"));
        grade.add(seed("BES006", "Desenvolvimento Web", 80, data(3, 20), 3, "Aplicacoes web",
                List.of("HTML semantico e acessibilidade", "Estilizacao responsiva", "Consumo de APIs", "Componentizacao", "Boas praticas de publicacao web"),
                escola, professor, curso, "BES003"));
        grade.add(seed("BES007", "Engenharia de Requisitos", 60, data(3, 23), 4, "Processos de software",
                List.of("Levantamento e negociacao de requisitos", "Especificacao funcional e nao funcional", "Historias de usuario", "Prototipacao", "Gestao de mudancas"),
                escola, professor, curso, "BES006"));
        grade.add(seed("BES009", "Analise e Projeto de Sistemas", 60, data(3, 25), 4, "Modelagem de sistemas",
                List.of("Modelagem UML", "Casos de uso", "Padroes de projeto introdutorios", "Arquitetura em camadas", "Documentacao tecnica"),
                escola, professor, curso, "BES007"));
        grade.add(seed("BES010", "Qualidade e Testes de Software", 60, data(3, 27), 5, "Garantia da qualidade",
                List.of("Planejamento de testes", "Testes unitarios, integracao e sistema", "Automacao de testes", "Metricas de qualidade", "Relatorio de defeitos"),
                escola, professor, curso, "BES009"));
        grade.add(seed("BES011", "Arquitetura de Software", 60, data(3, 29), 5, "Arquitetura e design",
                List.of("Atributos de qualidade", "Visoes arquiteturais", "Estilos arquiteturais", "Componentizacao", "Decisoes arquiteturais"),
                escola, professor, curso, "BES009"));
        grade.add(seed("BES012", "Sistemas Distribuidos", 60, data(4, 1), 6, "Infraestrutura distribuida",
                List.of("Comunicacao entre processos", "Concorrencia e sincronizacao", "Servicos distribuidos", "Tolerancia a falhas", "Escalabilidade"),
                escola, professor, curso, "BES011"));
        grade.add(seed("BES013", "DevOps e Computacao em Nuvem", 60, data(4, 3), 6, "Entrega continua e operacao",
                List.of("Integração e entrega continua", "Containers e orquestracao", "Infraestrutura como codigo", "Monitoramento", "Servicos em nuvem"),
                escola, professor, curso, "BES011"));
        grade.add(seed("BES014", "Seguranca da Informacao", 60, data(4, 5), 7, "Seguranca aplicada",
                List.of("Controles de acesso", "Criptografia aplicada", "Gestao de vulnerabilidades", "Seguranca em redes", "Boas praticas de conformidade"),
                escola, professor, curso, "BES012"));
        grade.add(seed("BES015", "Inteligencia Artificial Aplicada", 60, data(4, 7), 7, "IA e analise preditiva",
                List.of("Representacao do conhecimento", "Aprendizado supervisionado", "Modelos de classificacao", "Etica em IA", "Aplicacoes em software"),
                escola, professor, curso, "BES005"));
        grade.add(seed("BES008", "TCC", 60, data(3, 2), 8, "Pesquisa e inovacao",
                List.of("Definicao do problema", "Revisao de literatura", "Metodologia de desenvolvimento", "Implementacao e validacao", "Redacao e defesa"),
                escola, professor, curso, "BES010", "BES011", "BES013"));
        grade.add(seed("BES016", "Empreendedorismo e Inovacao", 60, data(4, 9), 8, "Gestao e inovacao",
                List.of("Modelos de negocio", "Validacao de proposta de valor", "Gestao de produtos digitais", "Inovacao aberta", "Pitch e apresentacao de solucoes"),
                escola, professor, curso, "BES009"));
    }

    private void adicionarGradeDireito(List<DisciplinaSeed> grade, Escola escola, Professor professor, Curso curso) {
        grade.add(seed("DIR001", "Introducao ao Estudo do Direito", 60, data(3, 1), 1, "Fundamentos juridicos",
                List.of("Norma juridica e ordenamento", "Fontes do direito", "Interpretação juridica", "Instituicoes do sistema de justica", "Argumentacao basica"),
                escola, professor, curso));
        grade.add(seed("DIR002", "Ciencia Politica e Teoria do Estado", 60, data(3, 3), 1, "Estado e sociedade",
                List.of("Formacao do Estado moderno", "Poder politico e soberania", "Sistemas de governo", "Instituicoes politicas brasileiras", "Participacao social"),
                escola, professor, curso));
        grade.add(seed("DIR003", "Direito Constitucional I", 60, data(3, 6), 2, "Constitucionalismo",
                List.of("Constituicao e supremacia constitucional", "Poder constituinte", "Organizacao do Estado", "Direitos fundamentais", "Controle de constitucionalidade introdutorio"),
                escola, professor, curso, "DIR001"));
        grade.add(seed("DIR004", "Sociologia Juridica", 60, data(3, 7), 2, "Direito e sociedade",
                List.of("Pensamento sociologico aplicado ao direito", "Instituicoes sociais", "Conflito e mudanca social", "Pluralismo juridico", "Acesso a justica"),
                escola, professor, curso));
        grade.add(seed("DIR005", "Direito Civil I", 60, data(3, 10), 3, "Relações privadas",
                List.of("Pessoa natural e juridica", "Capacidade civil", "Bens e patrimonio", "Fatos, atos e negocios juridicos", "Prescricao e decadencia"),
                escola, professor, curso, "DIR001"));
        grade.add(seed("DIR006", "Direito Penal I", 60, data(3, 12), 3, "Teoria penal",
                List.of("Princípios penais", "Aplicacao da lei penal", "Teoria do crime", "Culpabilidade", "Concurso de pessoas"),
                escola, professor, curso, "DIR001"));
        grade.add(seed("DIR009", "Teoria Geral do Processo", 60, data(3, 14), 4, "Jurisdição e processo",
                List.of("Jurisdição e competencia", "Sujeitos processuais", "Atos processuais", "Nulidades", "Fases procedimentais"),
                escola, professor, curso, "DIR001"));
        grade.add(seed("DIR010", "Direitos Humanos e Cidadania", 60, data(3, 16), 4, "Protecao de direitos",
                List.of("Sistemas internacionais de protecao", "Direitos civis e sociais", "Diversidade e inclusao", "Politicas publicas", "Controle de convencionalidade"),
                escola, professor, curso, "DIR003"));
        grade.add(seed("DIR011", "Direito Administrativo", 60, data(3, 18), 5, "Administracao publica",
                List.of("Principios administrativos", "Atos administrativos", "Poderes da administracao", "Servicos publicos", "Responsabilidade do Estado"),
                escola, professor, curso, "DIR003"));
        grade.add(seed("DIR012", "Direito Empresarial", 60, data(3, 20), 5, "Atividade empresarial",
                List.of("Empresario e sociedade empresaria", "Titulos de credito", "Estabelecimento empresarial", "Contratos mercantis", "Recuperacao judicial"),
                escola, professor, curso, "DIR005"));
        grade.add(seed("DIR013", "Direito Civil II", 60, data(3, 22), 6, "Obrigacoes e contratos",
                List.of("Obrigacoes civis", "Adimplemento e inadimplemento", "Contratos em especie", "Responsabilidade civil", "Garantias"),
                escola, professor, curso, "DIR005"));
        grade.add(seed("DIR014", "Processo Civil", 60, data(3, 24), 6, "Procedimentos civis",
                List.of("Peticao inicial e resposta", "Tutelas provisórias", "Provas no processo civil", "Sentenca e recursos", "Cumprimento de sentenca"),
                escola, professor, curso, "DIR009"));
        grade.add(seed("DIR007", "Processo Penal", 60, data(3, 5), 7, "Rito processual penal",
                List.of("Inquerito policial", "Acao penal", "Instrucao criminal", "Medidas cautelares", "Recursos penais"),
                escola, professor, curso, "DIR006", "DIR009"));
        grade.add(seed("DIR015", "Direito do Trabalho", 60, data(3, 26), 7, "Relações de trabalho",
                List.of("Fontes do direito do trabalho", "Contrato de trabalho", "Jornada e remuneracao", "Rescisao contratual", "Saude e seguranca laboral"),
                escola, professor, curso, "DIR005"));
        grade.add(seed("DIR008", "Direito Tributario", 60, data(3, 8), 8, "Sistema tributario",
                List.of("Competencia tributaria", "Obrigacao tributaria", "Credito tributario", "Especies de tributos", "Limitacoes ao poder de tributar"),
                escola, professor, curso, "DIR003", "DIR011"));
        grade.add(seed("DIR016", "Direito Previdenciario", 60, data(3, 28), 8, "Protecao social",
                List.of("Seguridade social", "Beneficios previdenciarios", "Custeio", "Regimes de previdencia", "Processo administrativo previdenciario"),
                escola, professor, curso, "DIR015"));
        grade.add(seed("DIR017", "Direito Ambiental", 60, data(3, 30), 9, "Sustentabilidade e regulacao",
                List.of("Princípios ambientais", "Licenciamento ambiental", "Responsabilidade ambiental", "Tutela coletiva", "Politicas de sustentabilidade"),
                escola, professor, curso, "DIR003"));
        grade.add(seed("DIR018", "Pratica Juridica Supervisionada I", 60, data(4, 1), 9, "Atuacao forense",
                List.of("Atendimento inicial", "Pesquisa processual", "Redacao de pecas", "Simulacao de audiencia", "Rotinas de nucleo de pratica"),
                escola, professor, curso, "DIR014", "DIR007"));
        grade.add(seed("DIR019", "Pratica Juridica Supervisionada II", 60, data(4, 3), 10, "Pratica profissional",
                List.of("Estrategia processual", "Atuacao consultiva", "Sustentacao oral simulada", "Acompanhamento de casos", "Etica profissional"),
                escola, professor, curso, "DIR018"));
        grade.add(seed("DIR020", "TCC em Direito", 60, data(4, 5), 10, "Pesquisa juridica",
                List.of("Projeto de pesquisa juridica", "Levantamento bibliografico", "Metodo cientifico", "Analise critica de fontes", "Escrita academica e defesa"),
                escola, professor, curso, "DIR008", "DIR018"));
    }

    private void adicionarGradeBiomedicina(List<DisciplinaSeed> grade, Escola escola, Professor professor, Curso curso) {
        grade.add(seed("BME001", "Anatomia Humana", 80, data(3, 1), 1, "Estruturas do corpo humano",
                List.of("Sistema esqueletico", "Sistema muscular", "Sistema nervoso", "Anatomia topografica", "Terminologia anatomica"),
                escola, professor, curso));
        grade.add(seed("BME002", "Biologia Celular e Molecular", 60, data(3, 4), 1, "Bases celulares",
                List.of("Membranas e organelas", "Ciclo celular", "DNA e RNA", "Expressao genica", "Sinalizacao celular"),
                escola, professor, curso));
        grade.add(seed("BME003", "Bioquimica", 60, data(3, 6), 2, "Processos bioquimicos",
                List.of("Estrutura de biomoleculas", "Enzimas", "Metabolismo energetico", "Vias anabolicas e catabolicas", "Regulacao metabolica"),
                escola, professor, curso, "BME002"));
        grade.add(seed("BME006", "Genetica Humana", 60, data(3, 9), 2, "Heranca biologica",
                List.of("Bases cromossomicas", "Hereditariedade mendeliana", "Genetica de populacoes", "Alteracoes geneticas", "Aconselhamento genetico"),
                escola, professor, curso, "BME002"));
        grade.add(seed("BME004", "Fisiologia", 60, data(3, 2), 3, "Funcoes integradas do organismo",
                List.of("Homeostase", "Sistema nervoso", "Sistema cardiovascular", "Sistema respiratorio", "Sistema endocrino"),
                escola, professor, curso, "BME001", "BME003"));
        grade.add(seed("BME007", "Microbiologia", 60, data(3, 12), 3, "Agentes biologicos",
                List.of("Bacterias e fungos", "Virus e replicacao viral", "Controle microbiologico", "Patogenicidade", "Diagnostico laboratorial basico"),
                escola, professor, curso, "BME002"));
        grade.add(seed("BME005", "Biofisica", 60, data(3, 7), 4, "Fenomenos fisicos em saude",
                List.of("Bioeletricidade", "Membranas e transporte", "Fluidos biologicos", "Termodinamica aplicada", "Radiacoes"),
                escola, professor, curso, "BME001", "BME003"));
        grade.add(seed("BME008", "Imunologia", 60, data(3, 15), 4, "Resposta imunologica",
                List.of("Imunidade inata", "Imunidade adaptativa", "Antigenos e anticorpos", "Hipersenbilidade", "Imunodiagnostico"),
                escola, professor, curso, "BME007"));
        grade.add(seed("BME009", "Patologia Geral", 60, data(3, 18), 5, "Mecanismos de doenca",
                List.of("Lesao celular", "Inflamacao", "Reparo tecidual", "Disturbios hemodinamicos", "Neoplasias"),
                escola, professor, curso, "BME004", "BME007"));
        grade.add(seed("BME010", "Parasitologia Clinica", 60, data(3, 20), 5, "Parasitos e saude coletiva",
                List.of("Protozoarios de interesse clinico", "Helmintos e ciclos biologicos", "Vetores", "Metodos diagnosticos", "Prevencao e controle"),
                escola, professor, curso, "BME007"));
        grade.add(seed("BME011", "Analises Clinicas", 80, data(3, 22), 6, "Diagnostico laboratorial",
                List.of("Coleta e processamento de amostras", "Bioquimica clinica", "Urinanalise", "Controle interno de qualidade", "Interpretacao de resultados"),
                escola, professor, curso, "BME009", "BME010"));
        grade.add(seed("BME012", "Hematologia", 60, data(3, 24), 6, "Estudo do sangue",
                List.of("Hemopoese", "Series eritrocitaria e leucocitaria", "Coagulacao", "Anemias", "Leituras hematologicas laboratoriais"),
                escola, professor, curso, "BME011"));
        grade.add(seed("BME013", "Radiologia e Diagnostico por Imagem", 60, data(3, 27), 7, "Imagem diagnostica",
                List.of("Princípios fisicos da imagem", "Protecao radiologica", "Modalidades diagnosticas", "Qualidade de imagem", "Aplicacoes clinicas"),
                escola, professor, curso, "BME005"));
        grade.add(seed("BME014", "Biotecnologia em Saude", 60, data(3, 29), 7, "Inovacao biomedica",
                List.of("Tecnicas de biologia molecular", "Cultura celular", "Biomarcadores", "Biosseguranca", "Aplicacoes translacionais"),
                escola, professor, curso, "BME006", "BME008"));
        grade.add(seed("BME015", "Estagio Supervisionado", 80, data(4, 2), 8, "Vivencia profissional em saude",
                List.of("Rotinas laboratoriais", "Padroes de biosseguranca", "Relacionamento com equipes multiprofissionais", "Registro tecnico das atividades", "Analise critica da pratica"),
                escola, professor, curso, "BME011", "BME013"));
        grade.add(seed("BME016", "TCC em Biomedicina", 60, data(4, 4), 8, "Pesquisa aplicada em biomedicina",
                List.of("Delimitacao do objeto de estudo", "Revisao de literatura", "Metodos de investigacao", "Tratamento e discussao de dados", "Defesa publica do trabalho"),
                escola, professor, curso, "BME014", "BME015"));
    }

    private void adicionarGradeMusica(List<DisciplinaSeed> grade, Escola escola, Professor professor, Curso curso) {
        grade.add(seed("MUS001", "Fundamentos de Teoria Musical", 60, data(3, 1), 1, "Linguagem musical",
                List.of("Notacao musical", "Ritmo e pulsacao", "Escalas e intervalos", "Formacao de acordes", "Leitura melodica"),
                escola, professor, curso));
        grade.add(seed("MUS002", "Percepcao e Solfejo", 60, data(3, 2), 1, "Escuta e leitura musical",
                List.of("Ditado ritmico", "Ditado melodico", "Solfejo tonal", "Percepcao de timbres", "Escuta analitica"),
                escola, professor, curso));
        grade.add(seed("MUS003", "Historia da Musica", 60, data(3, 6), 2, "Contextos historicos da musica",
                List.of("Musica antiga", "Musica barroca e classica", "Musica romantica", "Musica brasileira", "Tendencias contemporaneas"),
                escola, professor, curso, "MUS001"));
        grade.add(seed("MUS004", "Harmonia e Arranjo", 60, data(3, 8), 2, "Escrita musical",
                List.of("Campos harmonicos", "Encadeamento de acordes", "Voicings", "Arranjo para pequenos grupos", "Analise harmonica"),
                escola, professor, curso, "MUS001", "MUS002"));
        grade.add(seed("MUS005", "Musica e Tecnologia", 60, data(3, 3), 3, "Criacao musical digital",
                List.of("DAWs e fluxo de trabalho", "Midi e controladores", "Captacao e edicao de audio", "Mixagem introdutoria", "Organizacao de projetos"),
                escola, professor, curso, "MUS001"));
        grade.add(seed("MUS006", "Pratica Instrumental I", 60, data(3, 10), 3, "Execucao musical",
                List.of("Tecnica instrumental", "Repertorio aplicado", "Estudo individual orientado", "Interpretacao", "Performance em conjunto"),
                escola, professor, curso, "MUS002"));
        grade.add(seed("MUS007", "Canto Coral e Performance", 60, data(3, 13), 4, "Pratica vocal coletiva",
                List.of("Tecnica vocal", "Divisao de vozes", "Repertorio coral", "Expressao corporal", "Performance coletiva"),
                escola, professor, curso, "MUS006"));
        grade.add(seed("MUS008", "Composicao Musical", 60, data(3, 15), 4, "Criacao autoral",
                List.of("Motivos e desenvolvimento tematico", "Estruturas formais", "Composicao para diferentes formacoes", "Escrita criativa", "Revisao de partitura"),
                escola, professor, curso, "MUS004"));
        grade.add(seed("MUS009", "Producao Fonografica", 60, data(3, 18), 5, "Produção de obras gravadas",
                List.of("Pre-producao musical", "Gravacao em estudio", "Edicao e comping", "Organizacao de sessao", "Publicacao de fonogramas"),
                escola, professor, curso, "MUS005"));
        grade.add(seed("MUS010", "Sonorizacao e Acustica", 60, data(3, 20), 5, "Som ao vivo e ambientes sonoros",
                List.of("Fundamentos de acustica", "Microfones e captacao", "Mesa de som", "Monitoracao", "Planejamento tecnico de eventos"),
                escola, professor, curso, "MUS005"));
        grade.add(seed("MUS011", "Educacao Musical", 60, data(3, 22), 6, "Metodologias de ensino da musica",
                List.of("Didatica musical", "Planejamento de aula", "Praticas coletivas", "Avaliacao em educacao musical", "Recursos pedagogicos"),
                escola, professor, curso, "MUS003", "MUS007"));
        grade.add(seed("MUS012", "Regencia de Conjuntos", 60, data(3, 24), 6, "Conducao musical",
                List.of("Tecnica gestual", "Leitura de partitura", "Preparacao de ensaios", "Conducao de repertorio", "Direcao de pequenos conjuntos"),
                escola, professor, curso, "MUS007"));
        grade.add(seed("MUS013", "Trilha Sonora e Narrativas Sonoras", 60, data(3, 27), 7, "Musica para audiovisual",
                List.of("Funcao dramatica da musica", "Sincronismo e spotting", "Criação de temas", "Edicao para imagem", "Narrativas sonoras"),
                escola, professor, curso, "MUS008", "MUS009"));
        grade.add(seed("MUS014", "Empreendedorismo Cultural", 60, data(3, 29), 7, "Gestao de carreira artistica",
                List.of("Economia da cultura", "Modelos de financiamento", "Projetos culturais", "Marketing para artistas", "Direitos autorais"),
                escola, professor, curso, "MUS009"));
        grade.add(seed("MUS015", "Projeto Artistico Integrador", 60, data(4, 2), 8, "Curadoria e producao artistica",
                List.of("Concepcao de projeto autoral", "Planejamento de repertorio", "Producao executiva", "Identidade artistica", "Apresentacao publica"),
                escola, professor, curso, "MUS013", "MUS012"));
        grade.add(seed("MUS016", "TCC em Musica", 60, data(4, 4), 8, "Pesquisa e criacao musical",
                List.of("Projeto final em musica", "Revisao bibliografica e repertorial", "Registro do processo criativo", "Memorial tecnico", "Defesa e recital final"),
                escola, professor, curso, "MUS015", "MUS014"));
    }

    private record DisciplinaSeed(
            String sigla,
            String descricao,
            Integer cargaHoraria,
            LocalDate dataCadastro,
            Integer semestre,
            String eixoTematico,
            List<String> topicos,
            Escola escola,
            Professor professor,
            List<Curso> cursos,
            List<String> prerequisitosSiglas
    ) {}
}
