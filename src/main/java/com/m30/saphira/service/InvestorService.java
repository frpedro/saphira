package com.m30.saphira.service;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvestorService {

    // referencia para repositorio
    private final InvestorRepository investorRepository;

    // cria um novo investidor
    public InvestorDTO criarInvestidor(Investor.PerfilInvestidor perfilInvestidor, InvestorDTO dto) {

        // valida se o email cadastrado já existe
        if(investorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // cria novo objeto de investidor
        Investor investor = new Investor();

        // preenche os dados
        investor.setEmail(dto.getEmail());
        investor.setNome(dto.getNome());
        investor.setPerfilInvestidor(perfilInvestidor);

        // persiste / salva no banco de dados
        Investor investidorSalvo = investorRepository.save(investor);

        // retorna o resultado
        return new InvestorDTO(investidorSalvo.getNome(), investidorSalvo.getEmail(), perfilInvestidor);

    }

    // lista todos investidores
    public List<Investor> listarTodosInvestidores() {
        return investorRepository.findAll();
    }

    // lista investidores por perfil
    public List<Investor> listarInvestidoresPorPefil(Investor.PerfilInvestidor perfil) {
        return investorRepository.findByPerfilInvestidor(perfil);
    }

    //busca investidor por id
    public Investor buscaInvestidorPorId(UUID id) {
        Optional<Investor> optionalInvestor = investorRepository.findById(id);
        return optionalInvestor.orElseThrow(() -> new RuntimeException("Investidor não encontrado"));
    }

    // busca investidor por nome
    public Investor buscaInvestidorPorNome(String nome) {
        Optional<Investor> optionalInvestor = investorRepository.findByNome(nome);
        return optionalInvestor.orElseThrow(() -> new RuntimeException("Investidor não encontrado"));
    }

}
