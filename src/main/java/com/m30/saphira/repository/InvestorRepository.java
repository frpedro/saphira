package com.m30.saphira.repository;

import com.m30.saphira.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestorRepository extends JpaRepository<Investor, UUID> {

    // busca investidor pelo nome
    Optional<Investor> findByNome (String nome);

    // busca investidor pelo email
    Optional<Investor> findByEmail (String email);

    // busca investidor por tipo de perfil
    List<Investor> findByPerfilInvestidor (Investor.PerfilInvestidor perfilInvestidor);

    // busca apenas investidores qualificados
    @Query("SELECT DISTINCT i.investidor FROM Investment i WHERE i.valorAplicado > :valor")
    List<Investor> findByInvestidorQualificado (double valor);

}
