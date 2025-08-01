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

    @PostMapping("/{id}/renovar")
    public ResponseEntity<EmprestimoResponseDTO> renovarEmprestimo(@PathVariable UUID id) {
        EmprestimoResponseDTO emprestimoRenovado = emprestimoService.renovarEmprestimo(id);
        return ResponseEntity.ok(emprestimoRenovado);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<EmprestimoResponseDTO>> ListarAtivos() {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarAtivos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/devolvidos")
    public ResponseEntity<List<EmprestimoResponseDTO>> ListarDevolvidos() {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarDevolvidos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<EmprestimoResponseDTO>> ListarAtrasados() {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarAtrasados();
        return ResponseEntity.ok(emprestimos);
    }
}
