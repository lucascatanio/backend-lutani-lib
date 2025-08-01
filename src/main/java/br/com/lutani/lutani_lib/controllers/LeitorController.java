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

import br.com.lutani.lutani_lib.dtos.EmprestimoResponseDTO;
import br.com.lutani.lutani_lib.dtos.LeitorRequestDTO;
import br.com.lutani.lutani_lib.dtos.LeitorResponseDTO;
import br.com.lutani.lutani_lib.services.EmprestimoService;
import br.com.lutani.lutani_lib.services.LeitorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leitores")
public class LeitorController {

    private final LeitorService leitorService;
    private final EmprestimoService emprestimoService;

    public LeitorController(LeitorService leitorService, EmprestimoService emprestimoService) {
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<LeitorResponseDTO>> listarTodos() {
        List<LeitorResponseDTO> leitores = leitorService.listarTodos();
        return ResponseEntity.ok(leitores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeitorResponseDTO> buscarPorId(@PathVariable UUID id) {
        LeitorResponseDTO leitor = leitorService.buscarPorId(id);
        return ResponseEntity.ok(leitor);
    }

    @PostMapping
    public ResponseEntity<LeitorResponseDTO> criarLeitor(@Valid @RequestBody LeitorRequestDTO requestDTO) {
        LeitorResponseDTO novoLeitor = leitorService.criarLeitor(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoLeitor.id()).toUri();
        return ResponseEntity.created(uri).body(novoLeitor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeitorResponseDTO> atualizarLeitor(@PathVariable UUID id, @Valid @RequestBody LeitorRequestDTO requestDTO) {
        LeitorResponseDTO leitorAtualizado = leitorService.atualizarLeitor(id, requestDTO);
        return ResponseEntity.ok(leitorAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarLeitor(@PathVariable UUID id) {
        leitorService.inativarLeitor(id);
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<List<EmprestimoResponseDTO>> buscarHistoricoPorLeitor(@PathVariable UUID id){
        List<EmprestimoResponseDTO> historico = emprestimoService.listarHistoricoPorLeitor(id);
        return ResponseEntity.ok(historico);
    }
}
