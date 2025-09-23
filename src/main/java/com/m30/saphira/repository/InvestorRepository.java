package com.m30.saphira.repository;

import com.m30.saphira.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestorRepository extends JpaRepository<Investor, UUID> {

    Optional<Investor> findById(UUID id);

    Optional<Investor> findByName(String name);

    List<Investor> findByInvestorProfile(Investor.InvestorProfile investorProfile);

    // find only qualified investors
    @Query("SELECT DISTINCT i.investor FROM Investment i WHERE i.appliedValue > :value")
    List<Investor> findByQualifiedInvestor (double value);

    boolean existsByEmail(String email);
}
