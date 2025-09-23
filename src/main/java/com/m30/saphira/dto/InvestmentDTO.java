package com.m30.saphira.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class InvestmentDTO {

    @NotNull
    @Schema(description = "Unique UUID referring to each investor.", example = "61ff10d1-d26c-460d-9d8d-1807a879d5c5")
    private UUID investorId;

    @NotBlank(message = "Ativo é um campo obrigatório")
    @Schema(description = "Action/investment/asset was acquired by the investor.", example = "PETR4")
    private String asset;

    @NotNull(message = "O valor aplicado é obrigatório")
    @Positive(message = "O valor aplicado deve ser maior que zero")
    @Schema(description = "Applied value by the investor.", example = "2000.00")
    private double appliedValue;

}
