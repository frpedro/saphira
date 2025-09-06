package com.m30.saphira.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class InvestmentDTO {

    @NotNull
    private UUID investorId;

    @NotBlank(message = "Ativo é um campo obrigatório")
    private String ativo;

    @NotNull(message = "O valor aplicado é obrigatório")
    @Positive(message = "O valor aplicado deve ser maior que zero")
    private double valorAplicado;

}
