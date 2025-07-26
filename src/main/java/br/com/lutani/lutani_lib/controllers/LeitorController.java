package br.com.lutani.lutani_lib.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lutani.lutani_lib.dtos.LeitorRequestDTO;
import br.com.lutani.lutani_lib.dtos.LeitorResponseDTO;
import br.com.lutani.lutani_lib.services.LeitorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leitores")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @PostMapping
    public ResponseEntity<LeitorResponseDTO> criarLeitor(@Valid @RequestBody LeitorRequestDTO requestDTO) {
        LeitorResponseDTO novoLeitor = leitorService.criarLeitor(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoLeitor.id()).toUri();
        return ResponseEntity.created(uri).body(novoLeitor);
    }
}
