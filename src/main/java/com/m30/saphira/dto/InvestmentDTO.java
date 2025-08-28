package com.m30.saphira.dto;
import com.m30.saphira.model.Investor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InvestmentDTO {

    private String nome;
    private String ativo;
    private double valorAplicado;

}
