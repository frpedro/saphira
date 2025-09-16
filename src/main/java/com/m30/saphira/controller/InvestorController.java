package com.m30.saphira.controller;
import com.m30.saphira.config.apiresponse.DeleteResponses;
import com.m30.saphira.config.apiresponse.GetResponses;
import com.m30.saphira.config.apiresponse.PostResponses;
import com.m30.saphira.config.apiresponse.PutResponses;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investor;
import com.m30.saphira.service.InvestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


// cria os construtores necessários
@RequiredArgsConstructor

// indica que a classe é controller e define rota
@RestController
@RequestMapping("/investidor")
@Tag(name = "Investidores")
public class InvestorController {

    // referencia para service
    private final InvestorService investorService;

    // rota post para criar novo investidor
    @PostMapping
    @PostResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Cria um novo investidor")
    public ResponseEntity<InvestorDTO> criarInvestidor(@Parameter @Valid @RequestBody InvestorDTO dto) {

        // cria novo investidor
        InvestorDTO novoInvestidor = investorService.criarInvestidor(dto.getPerfilInvestidor(), dto);

        // retorna o resultado
        return new ResponseEntity<>(novoInvestidor, HttpStatus.CREATED);
    }

    // rota get para listar todos investidores
    @GetMapping
    @GetResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Lista todos investidores do sistema")
    public ResponseEntity<List<InvestorDTO>> listarTodosInvestidores() {

        // lista todos investidores
        List<InvestorDTO> todosInvestidores = investorService.listarTodosInvestidores();

        // retorna o resultado
        return new ResponseEntity<>(todosInvestidores, HttpStatus.OK);
    }

    // rota get para procurar investidor especifico por id
    @GetMapping("/buscar/id/{id}")
    @GetResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Busca investidor por ID")
    public ResponseEntity<InvestorDTO> procurarInvestidorId(@Parameter @Valid @PathVariable UUID id) {

        // busca investidor
        InvestorDTO investidorEncontrado = investorService.listarInvestidorPorId(id);

        // retorna resultado
        return ResponseEntity.ok(investidorEncontrado);
    }

    // rota get para procurar investidor especifico por nome
    @GetMapping("/buscar/nome/{nome}")
    @GetResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Busca investidor por nome")
    public ResponseEntity<InvestorDTO> procurarInvestidorPorNome(@Parameter @Valid @PathVariable String nome) {

        // busca investidor
        InvestorDTO investidorEncontrado = investorService.listarInvestidorPorNome(nome);

        // retorna resultado
        return ResponseEntity.ok(investidorEncontrado);
    }

    // rota get para procurar investidores especificos por perfil
    @GetMapping("/buscar/perfil/{perfil}")
    @GetResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Lista investidores por tipo de perfil de investidor")
    public ResponseEntity<List<InvestorDTO>> procurarInvestidorPerfil(@Parameter @Valid @PathVariable Investor.PerfilInvestidor perfil) {

        // busca investidores
        List<InvestorDTO> investidoresEncontrados = investorService.listarInvestidoresPorPefil(perfil);

        // retorna resultado
        return ResponseEntity.ok(investidoresEncontrados);
    }

    // rota put para atualizar dados de um investidor
    @PutMapping("/atualizar/{id}")
    @PutResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Atualiza um investidor")
    public ResponseEntity<InvestorDTO> atualizarInvestidor(@Parameter @PathVariable UUID id, @Valid @RequestBody InvestorDTO investorDTO) {

        // atualiza dados
        InvestorDTO investidorAtualizado = investorService.atualizarInvestidor(id, investorDTO);

        // retorna resultado
        return ResponseEntity.ok(investidorAtualizado);
    }

    // rota delete para deletar um investidor
    @DeleteMapping("/deletar/{id}")
    @DeleteResponses // pacote de respostas http possiveis dentro dessa rota
    @Operation(description = "Deleta um investidor")
    public ResponseEntity<String> deletarInvestidor(@Parameter @Valid @PathVariable UUID id) {

        // exclui investidor
        investorService.excluirInvestidor(id);

        // retorna resultado com mensagem
        return ResponseEntity.ok("Investidor excluido com sucesso");
    }

}
