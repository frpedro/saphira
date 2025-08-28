package com.m30.saphira.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter

// table
@Entity
@Table(name = "investment")
public class Investment {

    // map
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "valor_aplicado")
    private double valorAplicado;

    @Column(name = "data_aplicacao")
    private LocalDate dataAplicacao;

    @Column
    private String ativo;

    // fk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investidor_id", foreignKey = @ForeignKey(name = "fk_investidor"))
    private Investor investidor;

}
