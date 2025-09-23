package com.m30.saphira.service;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.DataConflictException;
import com.m30.saphira.exception.ResourceNotFoundException;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorDTO createInvestor(Investor.InvestorProfile investorProfile, InvestorDTO dto) {

        if(investorRepository.existsByEmail(dto.getEmail())) {
            throw new DataConflictException("Email " + dto.getEmail() + " is already in use.");
        }

        Investor investor = new Investor();

        investor.setEmail(dto.getEmail());
        investor.setName(dto.getName());
        investor.setInvestorProfile(investorProfile);

        Investor savedInvestor = investorRepository.save(investor);

        return convertToDTO(savedInvestor);

    }

    public List<InvestorDTO> findAllInvestors() {

        List<Investor> allInvestors = investorRepository.findAll();

        return allInvestors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InvestorDTO> findInvestorByProfile(Investor.InvestorProfile profile) {

        List<Investor> investors = investorRepository.findByInvestorProfile(profile);

        return investors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InvestorDTO findInvestorsById(UUID id) {

        Optional<Investor> optionalInvestor = investorRepository.findById(id);

        Investor investor = optionalInvestor.orElseThrow(() ->
                new ResourceNotFoundException("Investor with ID " + id + " not found."));

        return convertToDTO(investor);
    }

    public InvestorDTO findInvestorsByName(String name) {

        Optional<Investor> optionalInvestor = investorRepository.findByName(name);

        Investor investor = optionalInvestor.orElseThrow(() ->
                new ResourceNotFoundException("Investor with name " + name + " not found."));

        return convertToDTO(investor);

    }

    public InvestorDTO updateInvestor(UUID id, InvestorDTO investorDTO) {

        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor with ID " + id + " not found."));

        if(!investor.getEmail().equals(investorDTO.getEmail()) && investorRepository.existsByEmail(investorDTO.getEmail())) {
            throw new DataConflictException("Email " + investorDTO.getEmail() + " is already in use.");
        }

        investor.setName(investorDTO.getName());
        investor.setEmail(investorDTO.getEmail());
        investor.setInvestorProfile(investorDTO.getInvestorProfile());

        Investor updatedInvestor = investorRepository.save(investor);

        return convertToDTO(updatedInvestor);
    }

    public void deleteInvestor(UUID id) {

        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor with ID " + id + " not found."));

        investorRepository.delete(investor);
    }

    private InvestorDTO convertToDTO(Investor investor) {
        return new InvestorDTO(
                investor.getName(),
                investor.getEmail(),
                investor.getInvestorProfile()
        );
    }

}
