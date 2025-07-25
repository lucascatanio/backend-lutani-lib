package br.com.lutani.lutani_lib.dtos;

public record LeitorRequestDTO(
  String nome,
  String cpf,
  String email,
  String telefone,
  String endereco
) {}
