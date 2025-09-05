package com.m30.saphira.controller;

import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestorRepository;
import com.m30.saphira.service.InvestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// cria os construtores necessários
@RequiredArgsConstructor

// indica que a classe é controller e define rota
@RestController
@RequestMapping("/investidor")
public class InvestorController {

    // referencia para service
    private final InvestorService investorService;

    // rota post para criar novo investidor
    @PostMapping
    public ResponseEntity<InvestorDTO> criarInvestidor(@RequestBody InvestorDTO dto) {
        // cria novo investidor
        InvestorDTO novoInvestidor = investorService.criarInvestidor(dto.getPerfilInvestidor(), dto);

        // retorna o resultado
        return new ResponseEntity<>(novoInvestidor, HttpStatus.CREATED);
    }

    // rota get para listar todos investidores
    @GetMapping
    public ResponseEntity<List<InvestorDTO>> listarTodosInvestidores() {

        // lista todos investidores
        List<Investor> todosInvestidores = investorService.listarTodosInvestidores();

        // converte model para dto
        List<InvestorDTO> todosInvestidoresDTO = todosInvestidores.stream()
                .map(investor -> new InvestorDTO(
                        investor.getNome(),
                        investor.getEmail(),
                        investor.getPerfilInvestidor()
                ))
                .collect(Collectors.toList());

        // retorna o resultado
        return new ResponseEntity<>(todosInvestidoresDTO, HttpStatus.OK);
    }

    // rota get para procurar investidor especifico por id
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<InvestorDTO> procurarInvestidorId(@PathVariable UUID id) {

        // busca investidor
        Investor investidorEncontrado = investorService.listarInvestidorPorId(id);

        // converte model para dto
        InvestorDTO investidorEncontradoDTO = new InvestorDTO(investidorEncontrado);

        // retorna resultado
        return ResponseEntity.ok(investidorEncontradoDTO);
    }

    // rota get para procurar investidor especifico por nome
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<InvestorDTO> procurarInvestidorNome(@PathVariable String nome) {

        // busca investidor
        Investor investidorEncontrado = investorService.listarInvestidorPorNome(nome);

        // converte model para dto
        InvestorDTO investidorEncontradoDTO = new InvestorDTO(investidorEncontrado);

        // retorna resultado
        return ResponseEntity.ok(investidorEncontradoDTO);
    }

    // rota get para procurar investidores especificos por perfil
    @GetMapping("/buscar/perfil/{perfil}")
    public ResponseEntity<List<InvestorDTO>> procurarInvestidorPerfil(@PathVariable Investor.PerfilInvestidor perfil) {

        // busca investidores
        List<Investor> investidoresEncontrados = investorService.listarInvestidoresPorPefil(perfil);

        // converte model para dto
        List<InvestorDTO> investidoresDTO = investidoresEncontrados.stream()
                .map(InvestorDTO::new)
                .toList();

        // retorna resultado
        return ResponseEntity.ok(investidoresDTO);
    }

    // rota put para atualizar dados de um investidor
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<InvestorDTO> atualizarInvestidor(@PathVariable UUID id, @RequestBody InvestorDTO investorDTO) {

        // atualiza dados
        InvestorDTO investidorAtualizado = investorService.atualizarInvestidor(id, investorDTO);

        // retorna resultado
        return ResponseEntity.ok(investidorAtualizado);
    }

    // rota delete para deletar um investidor
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarInvestidor(@PathVariable UUID id) {

        // exclui investidor
        investorService.excluirInvestidor(id);

        // retorna resultado com mensagem
        return ResponseEntity.ok("O investidor foi excluido com sucesso");
    }

}
