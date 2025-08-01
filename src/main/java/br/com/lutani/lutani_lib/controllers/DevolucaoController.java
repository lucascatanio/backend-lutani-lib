package br.com.lutani.lutani_lib.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lutani.lutani_lib.dtos.EmprestimoResponseDTO;
import br.com.lutani.lutani_lib.services.EmprestimoService;

@RestController
@RequestMapping("/api/devolucoes")
public class DevolucaoController {

    private final EmprestimoService emprestimoService;

    public DevolucaoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> realizarDevolucao(@RequestParam String codigoExemplar) {
        EmprestimoResponseDTO emprestimoFinalizado = emprestimoService.realizarDevolucao(codigoExemplar);
        return ResponseEntity.ok(emprestimoFinalizado);
    }
}
