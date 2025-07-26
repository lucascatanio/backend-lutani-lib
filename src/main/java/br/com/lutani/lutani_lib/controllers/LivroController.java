package br.com.lutani.lutani_lib.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lutani.lutani_lib.dtos.LivroRequestDTO;
import br.com.lutani.lutani_lib.dtos.LivroResponseDTO;
import br.com.lutani.lutani_lib.services.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        List<LivroResponseDTO> livros = livroService.listarTodos();

        return ResponseEntity.ok(livros);
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criarLivro(@RequestBody LivroRequestDTO requestDTO) {
        LivroResponseDTO novoLivro = livroService.criarLivro(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoLivro.id()).toUri();
        return ResponseEntity.created(uri).body(novoLivro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable UUID id) {
        LivroResponseDTO livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }
}
