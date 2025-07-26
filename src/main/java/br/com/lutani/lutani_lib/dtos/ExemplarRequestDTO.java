package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExemplarRequestDTO(
        @NotNull(message = "O ID do livro não pode ser nulo.")
        UUID livroId,
        @NotBlank(message = "O código do exemplar pode estar em branco.")
        String codigoExemplar,
        @NotBlank(message = "O status não pode estar em branco.")
        String status
        ) {

}
