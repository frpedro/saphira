package com.m30.saphira.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "investor")
public class Investor {

    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "investor_profile")
    private InvestorProfile investorProfile;

    public enum InvestorProfile {
        conservador,
        moderado,
        arrojado
    }

}


