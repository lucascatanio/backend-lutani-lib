package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record ExemplarRequestDTO(
  UUID livroId,
  String codigoExemplar,
  String status
) {}
