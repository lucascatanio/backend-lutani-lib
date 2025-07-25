package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record UsuarioResponseDTO(
    UUID id,
    String nome,
    String nomeUsuario,
    NivelAcessoResponseDTO nivelAcesso
) {}