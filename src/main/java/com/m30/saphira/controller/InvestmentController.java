package com.m30.saphira.controller;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investment;
import com.m30.saphira.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// cria os construtores necessários
@RequiredArgsConstructor

// indica que a classe é controller e define a rota
@Controller
@RequestMapping("/investimento")
public class InvestmentController {

    // referencia para service
    private final InvestmentService investmentService;

    // rota post para criar novo investimento
    @PostMapping
    public ResponseEntity<InvestmentDTO> criarInvestimento (@RequestBody InvestmentDTO dto) {

        // cria novo investimento
        InvestmentDTO novoInvestimento = investmentService.createInvestment(
                dto.getNome(), dto.getAtivo(), dto.getValorAplicado());

        // retorna o resultado
        return new ResponseEntity<>(novoInvestimento, HttpStatus.CREATED);
    }

    // rota get para listar todos investimentos
    @GetMapping("/todos")
    public ResponseEntity<List<InvestmentDTO>> listarTodosInvestimentos() {
        
        // cria investimento
        List<Investment> todosInvestimentos = investmentService.listarTodosInvestimentos();

        // muda de model para dto
        List<InvestmentDTO> todosInvestimentosDTO = todosInvestimentos.stream()
                .map(investment -> new InvestmentDTO(
                        investment.getInvestidor().getNome(),
                        investment.getAtivo(),
                        investment.getValorAplicado()
                ))
                .collect(Collectors.toList());

        // retorna resultado
        return new ResponseEntity<>(todosInvestimentosDTO, HttpStatus.OK);
    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity<InvestmentDTO> buscaInvestimentosPorAtivo(@PathVariable String ativo) {

        Investment ativoEncontrado = investmentService.buscaInvestimentosPorAtivo(ativo);

        InvestmentDTO ativoEncontradoDTO = new InvestmentDTO(ativoEncontrado);

        return ResponseEntity.ok(ativoEncontradoDTO);
    }

}
