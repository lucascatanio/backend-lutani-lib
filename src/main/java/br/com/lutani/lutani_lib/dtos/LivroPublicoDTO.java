package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

public record LivroPublicoDTO(
        UUID id,
        String titulo,
        String autor,
        String editora,
        Integer anoPublicacao,
        long exemplaresDisponiveis
        ) {

}
