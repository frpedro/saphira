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
    @Size(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres")
    @Schema(description = "Investor name", example = "Pedro Fernandes")
    private final String name;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Formato de email inválido")
    @Schema(description = "Investor email", example = "investor@gmail.com")
    private final String email;

    @NotNull
    @Schema(description = "Investor profile type: the way they would like to invest - conservador, moderado ou arrojado",
            example = "arrojado")
    private final Investor.InvestorProfile investorProfile;

    public InvestorDTO(Investor investor) {
        this.name = investor.getName();
        this.email = investor.getEmail();
        this.investorProfile = investor.getInvestorProfile();
    }

}
