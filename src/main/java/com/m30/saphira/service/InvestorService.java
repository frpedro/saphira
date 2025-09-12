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

    // referencia para repositorio
    private final InvestorRepository investorRepository;

    // cria um novo investidor
    public InvestorDTO criarInvestidor(Investor.PerfilInvestidor perfilInvestidor, InvestorDTO dto) {

        // valida se o email cadastrado já existe
        if(investorRepository.existsByEmail(dto.getEmail())) {
            throw new DataConflictException("O email " + dto.getEmail() + " já está em uso.");
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
    public List<InvestorDTO> listarTodosInvestidores() {

        List<Investor> todosInvestidores = investorRepository.findAll();

        return todosInvestidores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // converte model da classe acima para dto
    private InvestorDTO convertToDTO(Investor investor) {
        return new InvestorDTO(
                investor.getNome(),
                investor.getEmail(),
                investor.getPerfilInvestidor()
        );
    }

    // lista investidores por perfil
    public List<InvestorDTO> listarInvestidoresPorPefil(Investor.PerfilInvestidor perfil) {

        List<Investor> investidoresPorPerfil = investorRepository.findByPerfilInvestidor(perfil);

        return investidoresPorPerfil.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //busca investidor por id
    public InvestorDTO listarInvestidorPorId(UUID id) {

        Optional<Investor> optionalInvestor = investorRepository.findById(id);

        Investor investor = optionalInvestor.orElseThrow(() ->
                new ResourceNotFoundException("Investidor(a) com ID " + id + " não encontrado"));

        return convertToDTO(investor);
    }

    // busca investidor por nome
    public InvestorDTO listarInvestidorPorNome(String nome) {

        Optional<Investor> optionalInvestor = investorRepository.findByNome(nome);

        Investor investor = optionalInvestor.orElseThrow(() ->
                new ResourceNotFoundException("Investidor(a) " + nome + " não encontrado."));

        return convertToDTO(investor);

    }

    // atualiza dados do investidor
    public InvestorDTO atualizarInvestidor (UUID id, InvestorDTO investorDTO) {

        // valida se existe
        Investor investidorExistente = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investidor(a) com ID " + id + " não encontrado"));

        // valida se o email adicionado já existe
        if(!investidorExistente.getEmail().equals(investorDTO.getEmail()) && investorRepository.existsByEmail(investorDTO.getEmail())) {
            throw new DataConflictException("O email " + investorDTO.getEmail() + " já está em uso.");
        }

        // seta novos dados atualizados
        investidorExistente.setNome(investorDTO.getNome());
        investidorExistente.setEmail(investorDTO.getEmail());
        investidorExistente.setPerfilInvestidor(investorDTO.getPerfilInvestidor());

        // salva objeto atualizado no banco
        Investor investidorAtualizado = investorRepository.save(investidorExistente);

        // retorna resultado da atualização
        return new InvestorDTO(
                investidorAtualizado.getNome(),
                investidorAtualizado.getEmail(),
                investidorAtualizado.getPerfilInvestidor()
        );
    }

    // exclui investidor
    public void excluirInvestidor (UUID id) {

        // valida se existe
        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investidor(a) com ID " + id + " não encontrado"));

        // deleta do banco
        investorRepository.delete(investor);
    }

}
