package com.m30.saphira.service;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.InvalidInvestmentException;
import com.m30.saphira.model.Investment;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvestmentService {

    // referência para os repositórios
    private final InvestmentRepository investmentRepository;
    private final InvestorRepository investorRepository;

    // cria um novo investimento
    public InvestmentDTO createInvestment(String nome, String ativo, double valorAplicado) {

        // valida se os campos estão preechidos corretamente
        if (nome == null || nome.isBlank() || ativo == null || ativo.isBlank() || valorAplicado <= 0) {
            throw new InvalidInvestmentException("Valores inválidos para criar investimento");
        }

        // busca investidor pelo nome e lança exeção se não existir
        Investor investidor = investorRepository.findByNome(nome)
                    .orElseThrow(() -> new InvalidInvestmentException("Investidor não encontrado"));

        // cria um novo objeto de investimento
        Investment investment = new Investment();

        //preenche os dados
        investment.setAtivo(ativo);
        investment.setValorAplicado(valorAplicado);
        investment.setDataAplicacao(LocalDate.now());
        investment.setInvestidor(investidor);

        // persiste / salva no banco de dados
        investmentRepository.save(investment);

        // retorna resultado da criação
        return new InvestmentDTO(investidor.getNome(), ativo, valorAplicado);

    }

    // lista todos investimentos
    public List<Investment> listarTodosInvestimentos() {
        return investmentRepository.findAll();
    }

    // lista investimentos filtrados por investidor
    public List<Investment> buscaInvestimentosPorInvestidor(UUID investidor) {
        return investmentRepository.findByInvestidor_Id(investidor);
    }

    // busca investimento por ativo
    public List<Investment> buscaInvestimentosPorAtivo(String ativo) {
        return investmentRepository.findByAtivo(ativo);
    }

    // atualiza investimento
    public InvestmentDTO atualizarInvestimento (UUID id, InvestmentDTO investmentDTO) {

        // valida se existe
        Investment investimentoExistente = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

        // seta novos dados atualizados
        investimentoExistente.setAtivo(investmentDTO.getAtivo());
        investimentoExistente.setValorAplicado(investmentDTO.getValorAplicado());
        investimentoExistente.setDataAplicacao(LocalDate.now());

        // salva objeto atualizado no banco
        Investment investimentoAtualizado = investmentRepository.save(investimentoExistente);

        // retorna resultado da atualização
        return new InvestmentDTO(
                investimentoAtualizado.getInvestidor().getNome(),
                investimentoAtualizado.getAtivo(),
                investimentoAtualizado.getValorAplicado()
        );
    }

    // exclui investimento
    public void  excluirInvestimento (UUID id) {

        // valida se existe
        if(!investmentRepository.existsById(id)) {
            throw new RuntimeException("Investimento não encontrado");
        }

        // deleta do banco
        investmentRepository.deleteById(id);
    }

}

