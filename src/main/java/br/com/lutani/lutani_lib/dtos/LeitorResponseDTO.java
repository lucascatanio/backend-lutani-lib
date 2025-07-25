package br.com.lutani.lutani_lib.dtos;

import java.time.Instant;
import java.util.UUID;

public record LeitorResponseDTO(
  UUID id,
  String nome,
  String cpf,
  String email,
  String telefone,
  String endereco,
  String status,
  Instant dtInclusao,
  String identidadeTipo,
  String comprovResidenciaTipo
) {}
