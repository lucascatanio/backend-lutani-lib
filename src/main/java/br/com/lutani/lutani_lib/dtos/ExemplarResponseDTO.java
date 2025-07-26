package br.com.lutani.lutani_lib.dtos;

import java.time.Instant;
import java.util.UUID;

public record ExemplarResponseDTO(
  UUID id,
  String codigoExemplar,
  String status,
  LivroResumidoDTO livro,
  Instant dtInclusao,
  UsuarioResumidoDTO usrInclusao
) {}
