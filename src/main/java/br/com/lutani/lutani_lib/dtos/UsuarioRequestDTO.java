package br.com.lutani.lutani_lib.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        String nome,
        @NotBlank(message = "O nome de usuário não pode estar em branco.")
        String nomeUsuario,
        @NotBlank(message = "A senha não pode estar em branco.")
        @Size(min = 3, message = "A senha deve ter no mínimo 3 caracteres.")
        String senha,
        @NotNull(message = "O ID do nível de acesso não pode ser nulo.")
        Integer nivelAcessoId
        ) {

}
