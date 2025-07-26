package br.com.lutani.lutani_lib.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(
        @NotBlank(message = "O título não pode estar em branco.")
        @Size(min = 2, max = 255, message = "O título deve ter no mínimo 2 caracteres.")
        String titulo,
        @NotBlank(message = "O autor pode estar em branco.")
        String autor,
        @NotBlank(message = "O ISBN não pode estar em branco.")
        String isbn,
        String editora,
        @NotNull(message = "O ano de publicação não pode ser nulo.")
        @PastOrPresent(message = "O ano de publicação não pode ser no futuro.")
        Integer anoPublicacao,
        String genero
        ) {

}
