package com.m30.saphira.service;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.exception.ResourceNotFoundException;
import com.m30.saphira.model.Investment;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvestmentService {

    // referência para os repositórios
    private final InvestmentRepository investmentRepository;
    private final InvestorRepository investorRepository;

    // cria um novo investimento
    public InvestmentDTO createInvestment(InvestmentDTO dto) {

        // busca investidor pelo nome e lança exeção se não existir
        Investor investidor = investorRepository.findById(dto.getInvestorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Investidor " + dto.getInvestorId() + " não encontrado."));

        // cria um novo objeto de investimento
        Investment investment = new Investment();

        //preenche os dados
        investment.setAsset(dto.getAsset());
        investment.setAppliedValue(dto.getAppliedValue());
        investment.setApplicationDate(LocalDateTime.now());
        investment.setInvestor(investidor);

        // persiste / salva no banco de dados
        investmentRepository.save(investment);

        // retorna resultado da criação
        return new InvestmentDTO(investidor.getId(), dto.getAsset(), dto.getAppliedValue());

    }

    // lista todos investimentos
    public List<InvestmentDTO> listAllInvestments() {

        List<Investment> todosInvestimentos = investmentRepository.findAll();

        return todosInvestimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        }

    // converte model da classe acima para dto
    private InvestmentDTO convertToDTO(Investment investment) {
        return new InvestmentDTO(
                investment.getInvestor().getId(),
                investment.getAsset(),
                investment.getAppliedValue()
        );
    }

    // lista investimentos filtrados por investidor
    public List<InvestmentDTO> findInvestmentsByInvestor(UUID investidor) {

        List<Investment> investimentosDoInvestidor = investmentRepository.findByInvestor_Id(investidor);

        return investimentosDoInvestidor.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // busca investimento por ativo
    public List<InvestmentDTO> findInvestmentsByAsset(String ativo) {

        List<Investment> investidoresPorAtivo = investmentRepository.findByAsset(ativo);

        return investidoresPorAtivo.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // atualiza investimento
    public InvestmentDTO updateInvestment(UUID id, InvestmentDTO investmentDTO) {

        // valida se existe
        Investment investimentoExistente = investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investimento " + id + " não encontrado."));

        // seta novos dados atualizados
        investimentoExistente.setAsset(investmentDTO.getAsset());
        investimentoExistente.setAppliedValue(investmentDTO.getAppliedValue());
        investimentoExistente.setApplicationDate(LocalDateTime.now());

        // salva objeto atualizado no banco
        Investment investimentoAtualizado = investmentRepository.save(investimentoExistente);

        // retorna resultado da atualização
        return new InvestmentDTO(
                investimentoAtualizado.getInvestor().getId(),
                investimentoAtualizado.getAsset(),
                investimentoAtualizado.getAppliedValue()
        );
    }

    // exclui investimento
    public void deleteInvestment(UUID id) {

        // valida se existe
        Investment investimento = investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investimento " + id + " não encontrado."));

        // deleta do banco
        investmentRepository.deleteById(id);
    }

}

