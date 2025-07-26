package br.com.lutani.lutani_lib.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LeitorRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        String nome,
        @NotBlank(message = "O CPF não pode estar em branco.")
        @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
        String cpf,
        @NotBlank(message = "O e-mail não pode estar em branco.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,
        String telefone,
        String endereco
        ) {

}
