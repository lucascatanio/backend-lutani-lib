package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

record LivroResumidoDTO(UUID id, String titulo) {}

public record ExemplarResponseDTO(
  UUID id,
  String codigoExemplar,
  String status,
  LivroResumidoDTO livro
) {}
