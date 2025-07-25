package br.com.lutani.lutani_lib.dtos;

public record UsuarioRequestDTO(
  String nome,
  String nomeUsuario,
  String senha,
  Integer nivelAcessoId
) {}
