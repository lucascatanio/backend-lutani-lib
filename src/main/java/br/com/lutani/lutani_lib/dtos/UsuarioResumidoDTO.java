package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record UsuarioResumidoDTO(
  UUID id,
  String nomeUsuario
) {}