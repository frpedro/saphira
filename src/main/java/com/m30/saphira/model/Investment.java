package com.m30.saphira.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "investment")
public class Investment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "applied_value")
    private double appliedValue;

    @Column(name = "application_date")
    private LocalDateTime applicationDate;

    @Column
    private String asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_id", foreignKey = @ForeignKey(name = "fk_investor"))
    private Investor investor;

}
