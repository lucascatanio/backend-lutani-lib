package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record EmprestimoRequestDTO(
  String codigoExemplar,
  UUID leitorID
) {}
