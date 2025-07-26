package br.com.lutani.lutani_lib.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.LivroResponseDTO;
import br.com.lutani.lutani_lib.entities.Livro;
import br.com.lutani.lutani_lib.repositories.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<LivroResponseDTO> listarTodos() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private LivroResponseDTO toDTO(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getGenero(),
                livro.getDtInclusao(),
                livro.getDtAlteracao()
        );
    }
}
