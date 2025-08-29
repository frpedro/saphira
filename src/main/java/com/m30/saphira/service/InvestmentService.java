package com.m30.saphira.service;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.InvalidInvestmentException;
import com.m30.saphira.model.Investment;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class InvestmentService {

    // referência para os repositórios
    private InvestmentRepository investmentRepository;
    private InvestorRepository investorRepository;

    // cria um novo investimento
    public InvestmentDTO createInvestment(String nome, String ativo, double valorAplicado) {

        // valida se os campos estão preechidos corretamente
        if (nome == null || nome.isBlank() || ativo == null || ativo.isBlank() || valorAplicado <= 0) {
            throw new InvalidInvestmentException("Valores inválidos para criar investimento");
        }

        // busca investidor pelo nome e lança exeção se não existir
        Investor investidor = investorRepository.findByNome(nome)
                    .orElseThrow(() -> new InvalidInvestmentException("Investidor não encontrado"));

        // cria um novo objeto de investimento e preenche os dados
        Investment investment = new Investment();
        investment.setAtivo(ativo);
        investment.setValorAplicado(valorAplicado);
        investment.setDataAplicacao(LocalDate.now());
        investment.setInvestidor(investidor);

        // persiste / salva no banco de dados
        investmentRepository.save(investment);

        // retorna o resultado
        return new InvestmentDTO(investidor.getNome(), ativo, valorAplicado);

    }
}

