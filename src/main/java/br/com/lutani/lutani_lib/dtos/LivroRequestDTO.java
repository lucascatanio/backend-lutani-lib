package br.com.lutani.lutani_lib.dtos;

import br.com.lutani.lutani_lib.validation.AnoNaoFuturo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        @AnoNaoFuturo
        Integer anoPublicacao,
        @NotNull(message = "O id do genêro não pode ser nullo.")
        Integer generoId
        ) {

}
