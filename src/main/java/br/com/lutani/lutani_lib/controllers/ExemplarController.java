package br.com.lutani.lutani_lib.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lutani.lutani_lib.dtos.ExemplarRequestDTO;
import br.com.lutani.lutani_lib.dtos.ExemplarResponseDTO;
import br.com.lutani.lutani_lib.services.ExemplarService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exemplares")
public class ExemplarController {

    private final ExemplarService exemplarService;

    public ExemplarController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @PostMapping
    public ResponseEntity<ExemplarResponseDTO> criarExemplar(@Valid @RequestBody ExemplarRequestDTO requestDTO) {
        ExemplarResponseDTO novoExemplar = exemplarService.criarExemplar(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoExemplar.id()).toUri();
        return ResponseEntity.created(uri).body(novoExemplar);
    }

    @GetMapping
    public ResponseEntity<List<ExemplarResponseDTO>> listarTodos() {
        return ResponseEntity.ok(exemplarService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExemplarResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(exemplarService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExemplarResponseDTO> atualizarExemplar(@PathVariable UUID id, @Valid @RequestBody ExemplarRequestDTO requestDTO) {
        return ResponseEntity.ok(exemplarService.atualizarExemplar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void descartarExemplar(@PathVariable UUID id) {
        exemplarService.descartarExemplar(id);
    }
}