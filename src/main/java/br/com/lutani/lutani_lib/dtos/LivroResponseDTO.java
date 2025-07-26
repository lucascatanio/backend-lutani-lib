package br.com.lutani.lutani_lib.dtos;

import java.time.Instant;
import java.util.UUID;

public record LivroResponseDTO(
  UUID id,
  String titulo,
  String autor,
  String isbn,
  String editora,
  Integer anoPublicacao,
  String genero,
  Instant dtInclusao,
  Instant dtAlteracao,
  UsuarioResumidoDTO usrInclusao,
  UsuarioResumidoDTO usrAlteracao
) {}
