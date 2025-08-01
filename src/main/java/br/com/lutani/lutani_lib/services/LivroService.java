package br.com.lutani.lutani_lib.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.GeneroDTO;
import br.com.lutani.lutani_lib.dtos.LivroPublicoDTO;
import br.com.lutani.lutani_lib.dtos.LivroRequestDTO;
import br.com.lutani.lutani_lib.dtos.LivroResponseDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Genero;
import br.com.lutani.lutani_lib.entities.Livro;
import br.com.lutani.lutani_lib.entities.Usuario;
import br.com.lutani.lutani_lib.enums.StatusExemplar;
import br.com.lutani.lutani_lib.exceptions.RecursoNaoEncontradoException;
import br.com.lutani.lutani_lib.exceptions.RegraDeNegocioException;
import br.com.lutani.lutani_lib.repositories.ExemplarRepository;
import br.com.lutani.lutani_lib.repositories.GeneroRepository;
import br.com.lutani.lutani_lib.repositories.LivroRepository;
import br.com.lutani.lutani_lib.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final GeneroRepository generoRepository;
    private final ExemplarRepository exemplarRepository;

    public LivroService(LivroRepository livroRepository, UsuarioRepository usuarioRepository,
            GeneroRepository generoRepository, ExemplarRepository exemplarRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.generoRepository = generoRepository;
        this.exemplarRepository = exemplarRepository;
    }

    public List<LivroResponseDTO> listarTodos() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LivroResponseDTO criarLivro(LivroRequestDTO requestDTO) {
        if (requestDTO.isbn() != null && livroRepository.findByIsbn(requestDTO.isbn()).isPresent()) {
            throw new RegraDeNegocioException("Já existe um livro cadastrado com este ISBN.");
        }

        Genero genero = generoRepository.findById(requestDTO.generoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gênero não encontrado."));

        Livro novoLivro = toEntity(requestDTO, genero);

        Livro livroSalvo = livroRepository.saveAndFlush(novoLivro);

        return toResponseDTO(livroSalvo);
    }

    public LivroResponseDTO buscarPorId(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado com o ID: " + id));

        return toResponseDTO(livro);
    }

    @Transactional
    public LivroResponseDTO atualizarLivro(UUID id, LivroRequestDTO requestDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado com o ID: " + id));

        if (requestDTO.isbn() != null && livroRepository.findByIsbn(requestDTO.isbn())
                .filter(livro -> !livro.getId().equals(id))
                .isPresent()) {
            throw new RegraDeNegocioException("ISBN já cadastrado em outro livro.");
        }

        Genero novoGenero = generoRepository.findById(requestDTO.generoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gênero não encontrado."));

        livroExistente.setTitulo(requestDTO.titulo());
        livroExistente.setAutor(requestDTO.autor());
        livroExistente.setIsbn(requestDTO.isbn());
        livroExistente.setEditora(requestDTO.editora());
        livroExistente.setAnoPublicacao(requestDTO.anoPublicacao());
        livroExistente.setGenero(novoGenero);

        Livro livroAtualizado = livroRepository.saveAndFlush(livroExistente);

        return toResponseDTO(livroAtualizado);
    }

    @Transactional
    public void deletarLivro(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado com o ID: " + id));

        if (livroRepository.hasExemplares(id)) {
            throw new RegraDeNegocioException("Não é possível deletar um livro que possui exemplares cadastrados.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuarioLogado = usuarioRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado para auditoria."));

        livro.setDeletedAt(Instant.now());
        livro.setDeletedBy(usuarioLogado);

        livroRepository.save(livro);
    }

    public List<LivroResponseDTO> buscar(String titulo, String autor) {
        List<Livro> livros = livroRepository.buscarPorTituloEAutor(titulo, autor);
        return livros.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<LivroPublicoDTO> buscarPublico(String titulo, String autor) {
        List<Livro> livrosEncontrados = livroRepository.buscarPorTituloEAutor(titulo, autor);

        return livrosEncontrados.stream()
                .map(this::toPublicoDTO)
                .collect(Collectors.toList());
    }

    private Livro toEntity(LivroRequestDTO dto, Genero genero) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setIsbn(dto.isbn());
        livro.setEditora(dto.editora());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setGenero(genero);
        return livro;
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
        GeneroDTO generoDTO = new GeneroDTO(
                livro.getGenero().getId(),
                livro.getGenero().getNome()
        );

        UsuarioResumidoDTO usrInclusaoDTO = (livro.getUsrInclusao() != null)
                ? new UsuarioResumidoDTO(livro.getUsrInclusao().getId(), livro.getUsrInclusao().getNomeUsuario())
                : null;

        UsuarioResumidoDTO usrAlteracaoDTO = (livro.getUsrAlteracao() != null)
                ? new UsuarioResumidoDTO(livro.getUsrAlteracao().getId(), livro.getUsrAlteracao().getNomeUsuario())
                : null;

        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                generoDTO,
                livro.getDtInclusao(),
                livro.getDtAlteracao(),
                usrInclusaoDTO,
                usrAlteracaoDTO
        );
    }

    private LivroPublicoDTO toPublicoDTO(Livro livro) {
    long disponiveis = exemplarRepository.countByLivroIdAndStatus(livro.getId(), StatusExemplar.DISPONIVEL);

    return new LivroPublicoDTO(
        livro.getId(),
        livro.getTitulo(),
        livro.getAutor(),
        livro.getEditora(),
        livro.getAnoPublicacao(),
        disponiveis
    );
}
}
