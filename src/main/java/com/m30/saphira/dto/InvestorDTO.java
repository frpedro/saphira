package com.m30.saphira.dto;

import com.m30.saphira.model.Investor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class InvestorDTO {

    @NotBlank(message = "Nome é um campo obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome só pode conter letras e espaços")
    @Size(min = 3, max = 50, message = "Nome inválido")
    @Schema(description = "Nome do investidor", example = "Pedro Fernandes")
    private final String nome;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Formato de email inválido")
    @Schema(description = "Email do investidor", example = "investidor@gmail.com")
    private final String email;

    @NotNull
    @Schema(description = "Tipo de perfil do investidor: a forma que ele gostaria de investir - conservador, moderado ou arrojado",
            example = "arrojado")
    private final Investor.PerfilInvestidor perfilInvestidor;

    public InvestorDTO(Investor investor) {
        this.nome = investor.getNome();
        this.email = investor.getEmail();
        this.perfilInvestidor = investor.getPerfilInvestidor();
    }

}
