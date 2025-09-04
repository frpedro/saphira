package com.m30.saphira.controller;

import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investor;
import com.m30.saphira.service.InvestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// cria os construtores necessários
@RequiredArgsConstructor

// declara que é controller e define rota
@RestController
@RequestMapping("/investidor")
public class InvestorController {

    // referencia para service
    private final InvestorService investorService;

    @PostMapping
    public ResponseEntity<InvestorDTO> criarInvestidor(@RequestBody InvestorDTO dto) {
        InvestorDTO novoInvestidor = investorService.criarInvestidor(dto.getPerfilInvestidor(), dto);
        return new ResponseEntity<>(novoInvestidor, HttpStatus.CREATED);
    }

    @GetMapping


    @GetMapping("/buscar/{id}")


    @PutMapping("/atualizar/{id}")


    @DeleteMapping("/deletar/{id}")



}
