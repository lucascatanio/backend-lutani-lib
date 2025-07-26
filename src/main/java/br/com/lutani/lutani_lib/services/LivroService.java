package br.com.lutani.lutani_lib.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.LivroRequestDTO;
import br.com.lutani.lutani_lib.dtos.LivroResponseDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Livro;
import br.com.lutani.lutani_lib.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
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
            throw new RuntimeException("Já existe um livro cadastrado com este ISBN.");
        }

        Livro novoLivro = toEntity(requestDTO);

        Livro livroSalvo = livroRepository.save(novoLivro);

        return toResponseDTO(livroSalvo);
    }

    public LivroResponseDTO buscarPorId(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
                
        return toResponseDTO(livro);
    }

    private Livro toEntity(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setIsbn(dto.isbn());
        livro.setEditora(dto.editora());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setGenero(dto.genero());
        return livro;
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
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
                livro.getGenero(),
                livro.getDtInclusao(),
                livro.getDtAlteracao(),
                usrInclusaoDTO,
                usrAlteracaoDTO
        );
    }
}
