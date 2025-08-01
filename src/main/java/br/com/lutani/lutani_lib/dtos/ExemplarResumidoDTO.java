package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record ExemplarResumidoDTO(
  UUID id,
  String codigoExemplar,
  String tituloLivro 
) {}
