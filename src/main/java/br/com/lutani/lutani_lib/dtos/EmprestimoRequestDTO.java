package br.com.lutani.lutani_lib.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmprestimoRequestDTO(
        @NotBlank(message = "O código do exemplar pode estar em branco.")
        String codigoExemplar,
        @NotNull(message = "O ID do leitor não pode ser nulo.")
        UUID leitorID
        ) {

}
