package br.com.lutani.lutani_lib.dtos;

import java.time.Instant;
import java.util.UUID;

public record EmprestimoResponseDTO(
  UUID id,
  LeitorResumidoDTO leitor,
  ExemplarResumidoDTO exemplar,
  String status,
  Instant dtEmprestimo,
  Instant dtVencimento,
  Instant dtDevolucao,
  UsuarioResumidoDTO usrInclusao
) {}
