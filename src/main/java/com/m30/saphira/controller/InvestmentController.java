package com.m30.saphira.controller;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.model.Investment;
import com.m30.saphira.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// cria os construtores necessários
@RequiredArgsConstructor

// indica que a classe é controller e define a rota
@RestController
@RequestMapping("/investimentos")
public class InvestmentController {

    // referencia para service
    private final InvestmentService investmentService;

    // rota post para criar novo investimento
    @PostMapping
    public ResponseEntity<InvestmentDTO> criarInvestimento (@Valid @RequestBody InvestmentDTO dto) {

        // cria novo investimento
        InvestmentDTO novoInvestimento = investmentService.createInvestment(dto);

        // retorna o resultado
        return new ResponseEntity<>(novoInvestimento, HttpStatus.CREATED);
    }

    // rota get para listar todos investimentos
    @GetMapping("/todos")
    public ResponseEntity<List<InvestmentDTO>> listarTodosInvestimentos() {
        
        // cria investimento
        List<Investment> todosInvestimentos = investmentService.listarTodosInvestimentos();

        // converte model para dto
        List<InvestmentDTO> todosInvestimentosDTO = todosInvestimentos.stream()
                .map(investment -> new InvestmentDTO(
                        investment.getInvestidor().getId(),
                        investment.getAtivo(),
                        investment.getValorAplicado()
                ))
                .collect(Collectors.toList());

        // retorna resultado
        return new ResponseEntity<>(todosInvestimentosDTO, HttpStatus.OK);
    }

    // rota get para listar ativo especifico
    @GetMapping("/ativos/{ativo}")
    public ResponseEntity<List<InvestmentDTO>> buscaInvestimentosPorAtivo(@PathVariable String ativo) {

        // busca ativo
        List<Investment> ativosEncontrados = investmentService.buscaInvestimentosPorAtivo(ativo);

        // converte model para dto
        List<InvestmentDTO> ativosEncontradosDTO = ativosEncontrados.stream()
                .map(investment -> new InvestmentDTO(
                investment.getInvestidor().getId(),
                investment.getAtivo(),
                investment.getValorAplicado()
        ))
        .toList();

        // retorna o resultado
        return ResponseEntity.ok(ativosEncontradosDTO);
    }

    // rota put para atualizar investimento
    @PutMapping("/update/{id}")
    public ResponseEntity<InvestmentDTO> atualizarInvestimento(@PathVariable UUID id, @Valid @RequestBody InvestmentDTO dto) {

        // atualiza investimento
        InvestmentDTO investidorAtualizado = investmentService.atualizarInvestimento(id, dto);

        // retorna resultado
        return ResponseEntity.ok(investidorAtualizado);
    }

    // rota delete para deletar investimento
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletarInvestimento(@PathVariable UUID id) {

        // exclui investimento
       investmentService.excluirInvestimento(id);

       // retorna resultado
        return ResponseEntity.ok("Investimento exluido com sucesso");
    }

}
