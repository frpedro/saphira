package com.m30.saphira.service;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.InvalidInvestmentException;
import com.m30.saphira.model.Investment;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestorRepository investorRepository;

    // criar novo investimento
    public InvestmentDTO createInvestment(String nome, String ativo, double valorAplicado) {
        // 1. validação
        if (nome == null || nome.isBlank() || ativo == null || ativo.isBlank() || valorAplicado <= 0) {
            throw new InvalidInvestmentException("Valores inválidos para criar investimento");
        }


    Investment investment = new Investment();
    investment.setAtivo(ativo);
    Investment.setNome(nome);
    Investment.setValorAplicado(valorAplicado);


    InvestorRepository.save(investment);

}
}
