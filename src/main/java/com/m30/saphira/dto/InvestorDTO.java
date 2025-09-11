package com.m30.saphira.dto;

import com.m30.saphira.model.Investor;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class InvestorDTO {

    @NotBlank(message = "Nome é um campo obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome só pode conter letras e espaços")
    @Size(min = 3, max = 50, message = "Nome inválido")
    private final String nome;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Formato de email inválido")
    private final String email;

    @NotNull
    private final Investor.PerfilInvestidor perfilInvestidor;

    public InvestorDTO(Investor investor) {
        this.nome = investor.getNome();
        this.email = investor.getEmail();
        this.perfilInvestidor = investor.getPerfilInvestidor();
    }

}
