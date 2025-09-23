package com.m30.saphira.repository;

import com.m30.saphira.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {

    List<Investment> findByInvestor_Id(UUID investor);

    List<Investment> findByAsset(String asset);

}
