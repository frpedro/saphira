package com.m30.saphira.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InvestorDTO {

    @NotBlank(message = "Nome é um campo obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome só pode conter letras e espaços")
    @Size(min = 3, max = 50, message = "Nome inválido")
    private String nome;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull
    private Enum perfilInvestidor;
}
