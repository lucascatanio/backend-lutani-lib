package br.com.lutani.lutani_lib.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lutani.lutani_lib.dtos.LivroPublicoDTO;
import br.com.lutani.lutani_lib.services.LivroService;

@RestController
@RequestMapping("/api/publico/livros")
public class LivroPublicoController {

    private final LivroService livroService;

    public LivroPublicoController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LivroPublicoDTO>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor) {

        return ResponseEntity.ok(livroService.buscarPublico(titulo, autor));
    }
}
