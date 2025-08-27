package com.m30.saphira.repository;

import com.m30.saphira.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {

    // busca investimentos de um investidor
    List<Investment> findByInvestidor_Id(UUID investidor);

    // busca investimentos do ativo
    List<Investment> findByAtivo(String ativo);

}
