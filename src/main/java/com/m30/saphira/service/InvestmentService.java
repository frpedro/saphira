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

    private final InvestmentRepository investmentRepository;
    private final InvestorRepository investorRepository;

    public InvestmentDTO createInvestment(InvestmentDTO dto) {

        Investor investor = investorRepository.findById(dto.getInvestorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Investor with ID: " + dto.getInvestorId() + " not found"));

        Investment investment = new Investment();

        investment.setAsset(dto.getAsset());
        investment.setAppliedValue(dto.getAppliedValue());
        investment.setApplicationDate(LocalDateTime.now());
        investment.setInvestor(investor);

        investmentRepository.save(investment);

        return new InvestmentDTO(investor.getId(), dto.getAsset(), dto.getAppliedValue());

    }

    public List<InvestmentDTO> listAllInvestments() {

        List<Investment> allInvestments = investmentRepository.findAll();

        return allInvestments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        }

    private InvestmentDTO convertToDTO(Investment investment) {
        return new InvestmentDTO(
                investment.getInvestor().getId(),
                investment.getAsset(),
                investment.getAppliedValue()
        );
    }

    public List<InvestmentDTO> findInvestmentsByInvestor(UUID investor) {

        List<Investment> investmentsByInvestor = investmentRepository.findByInvestor_Id(investor);

        return investmentsByInvestor.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InvestmentDTO> findInvestmentsByAsset(String asset) {

        List<Investment> investorsByAsset = investmentRepository.findByAsset(asset);

        return investorsByAsset.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InvestmentDTO updateInvestment(UUID id, InvestmentDTO investmentDTO) {

        Investment existingInvestment = investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment with ID: " + id + " not found."));

        existingInvestment.setAsset(investmentDTO.getAsset());
        existingInvestment.setAppliedValue(investmentDTO.getAppliedValue());
        existingInvestment.setApplicationDate(LocalDateTime.now());

        Investment updatedInvestment = investmentRepository.save(existingInvestment);

        return new InvestmentDTO(
                updatedInvestment.getInvestor().getId(),
                updatedInvestment.getAsset(),
                updatedInvestment.getAppliedValue()
        );
    }

    public void deleteInvestment(UUID id) {

        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment with ID: " + id + " not found."));

        investmentRepository.deleteById(id);
    }
}

