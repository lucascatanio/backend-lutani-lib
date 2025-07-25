package br.com.lutani.lutani_lib.dtos;

public record LivroRequestDTO(
  String titulo,
  String autor,
  String isbn,
  String editora,
  Integer anoPublicacao,
  String genero
) {}
