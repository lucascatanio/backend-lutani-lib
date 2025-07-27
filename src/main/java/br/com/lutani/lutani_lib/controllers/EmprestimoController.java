package br.com.lutani.lutani_lib.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lutani.lutani_lib.dtos.EmprestimoRequestDTO;
import br.com.lutani.lutani_lib.dtos.EmprestimoResponseDTO;
import br.com.lutani.lutani_lib.services.EmprestimoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> realizarEmprestimo(@Valid @RequestBody EmprestimoRequestDTO requestDTO) {
        EmprestimoResponseDTO novoEmprestimo = emprestimoService.realizarEmprestimo(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoEmprestimo.id()).toUri();
        return ResponseEntity.created(uri).body(novoEmprestimo);
    }
}