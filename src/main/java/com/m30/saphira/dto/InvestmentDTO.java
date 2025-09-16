package com.m30.saphira.dto;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "UUID único referente a cada investidor.", example = "61ff10d1-d26c-460d-9d8d-1807a879d5c5")
    private UUID investorId;

    @NotBlank(message = "Ativo é um campo obrigatório")
    @Schema(description = "Ação/investimento/ativo que foi adquirido pelo investidor.", example = "PETR4")
    private String ativo;

    @NotNull(message = "O valor aplicado é obrigatório")
    @Positive(message = "O valor aplicado deve ser maior que zero")
    @Schema(description = "Quantia investida pelo investidor.", example = "2000.00")
    private double valorAplicado;

}
