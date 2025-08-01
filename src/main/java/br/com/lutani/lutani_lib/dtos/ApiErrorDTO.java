package br.com.lutani.lutani_lib.dtos;

import java.time.Instant;

public record ApiErrorDTO(
  Instant timestamp,
  Integer status,
  String error,
  String message,
  String path
) {
  
}
