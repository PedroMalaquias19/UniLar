package com.pedro.UniLar.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    String nome,
    String sobrenome,
    String email,
    @NotBlank(message = "A password é obrigatória")
    @Size(min = 8, max = 64, message = "A password deve ter entre 8 e 64 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$", message = "A password deve incluir letra maiúscula, letra minúscula, número e caracter especial")
    String password,
    String NIF,
    String telefone
) {
}