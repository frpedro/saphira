package com.m30.saphira.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter

// table
@Entity
@Table(name = "investor")
public class Investor {

    // map
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column
    private String nome;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_investidor")
    private PerfilInvestidor perfilInvestidor;

    // check
    public enum PerfilInvestidor {
        conservador,
        moderado,
        arrojado
    }

}


