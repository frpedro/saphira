package com.m30.saphira.controller;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.model.Investment;
import com.m30.saphira.service.InvestmentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// cria os construtores necessários
@RequiredArgsConstructor

// indica que a classe é controller e define a rota
@RestController
@RequestMapping("/investimentos")
@Validated
@Tag(name = "Investimentos")
public class InvestmentController {

    // referencia para service
    @Autowired
    private final InvestmentService investmentService;

    // rota post para criar novo investimento
    @PostMapping
    public ResponseEntity<InvestmentDTO> criarInvestimento (@Parameter @Valid @RequestBody InvestmentDTO dto) {

        // cria novo investimento
        InvestmentDTO novoInvestimento = investmentService.createInvestment(dto);

        // retorna o resultado
        return new ResponseEntity<>(novoInvestimento, HttpStatus.CREATED);
    }

    // rota get para listar todos investimentos
    @GetMapping("/todos")
    public ResponseEntity<List<InvestmentDTO>> listarTodosInvestimentos() {
        
        // cria investimento
        List<InvestmentDTO> todosInvestimentosDTO = investmentService.listarTodosInvestimentos();

        // retorna resultado
        return new ResponseEntity<>(todosInvestimentosDTO, HttpStatus.OK);
    }

    // rota get para listar investimentos de um investidor
    @GetMapping("/ativos/investidor")
    public ResponseEntity<List<InvestmentDTO>> buscaInvestimentosPorInvestidor(@Parameter @PathVariable UUID investidor) {

        // busca investimentos do investidor
        List<InvestmentDTO> investimentosPorInvestidor = investmentService.buscaInvestimentosPorInvestidor(investidor);

        // retorna resultado
        return new ResponseEntity<>(investimentosPorInvestidor, HttpStatus.OK);
    }

    // rota get para listar ativo especifico
    @GetMapping("/ativos/{ativo}")
    public ResponseEntity<List<InvestmentDTO>> buscaInvestimentosPorAtivo(@Parameter @Valid @PathVariable String ativo) {

        // busca ativo
        List<InvestmentDTO> ativosEncontradosDTO = investmentService.buscaInvestimentosPorAtivo(ativo);

        // retorna o resultado
        return ResponseEntity.ok(ativosEncontradosDTO);
    }

    // rota put para atualizar investimento
    @PutMapping("/update/{id}")
    public ResponseEntity<InvestmentDTO> atualizarInvestimento(@Parameter @PathVariable UUID id, @Valid @RequestBody InvestmentDTO dto) {

        // atualiza investimento
        InvestmentDTO investidorAtualizado = investmentService.atualizarInvestimento(id, dto);

        // retorna resultado
        return ResponseEntity.ok(investidorAtualizado);
    }

    // rota delete para deletar investimento
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletarInvestimento(@Parameter @PathVariable UUID id) {

        // exclui investimento
       investmentService.excluirInvestimento(id);

       // retorna resultado
        return ResponseEntity.ok("Investimento exluido com sucesso");
    }

}
