package br.com.lutani.lutani_lib.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lutani.lutani_lib.dtos.UsuarioRequestDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResponseDTO;
import br.com.lutani.lutani_lib.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO requestDTO) {
        UsuarioResponseDTO novoUsuario = usuarioService.criarUsuario(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoUsuario.id()).toUri();

        return ResponseEntity.created(uri).body(novoUsuario);
    }
}
