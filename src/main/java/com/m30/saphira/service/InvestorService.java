package com.m30.saphira.service;

import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvestorService {

    // referencia para repositorio
    private final InvestorRepository investorRepository;

    // cria um novo investidor
    public InvestorDTO criarInvestidor(String nome, String email, Investor.PerfilInvestidor perfilInvestidor, InvestorDTO dto) {

        // valida se o email cadastrado já existe
        if(investorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // cria novo objeto de investidor
        Investor investor = new Investor();

        // preenche os dados
        investor.setEmail(email);
        investor.setNome(nome);
        investor.setPerfilInvestidor(perfilInvestidor);

        // persiste / salva no banco de dados
        investorRepository.save(investor);

        // retorna o resultado
        return new InvestorDTO(investor.getNome(), email, perfilInvestidor);

    }

}
