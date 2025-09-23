package com.m30.saphira.controller;
import com.m30.saphira.config.apiresponse.DeleteResponses;
import com.m30.saphira.config.apiresponse.GetResponses;
import com.m30.saphira.config.apiresponse.PostResponses;
import com.m30.saphira.config.apiresponse.PutResponses;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.service.InvestmentService;
import io.swagger.v3.oas.annotations.Operation;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/investments")
@Validated
@Tag(name = "Investments", description = "Endpoints for managing investment data.")
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping
    @PostResponses
    @Operation(summary = "Create a new investment", description = "Creates a new investment record.")
    public ResponseEntity<InvestmentDTO> createInvestment(@Parameter @Valid @RequestBody InvestmentDTO dto) {

        InvestmentDTO newInvestment = investmentService.createInvestment(dto);

        return new ResponseEntity<>(newInvestment, HttpStatus.CREATED);
    }

    @GetMapping
    @GetResponses
    @Operation(summary = "List investments", description = "List all investments.")
    public ResponseEntity<List<InvestmentDTO>> findAllInvestments() {

        List<InvestmentDTO> allInvestmentsDTO = investmentService.listAllInvestments();

        return new ResponseEntity<>(allInvestmentsDTO, HttpStatus.OK);
    }

    @GetMapping("/investor/{investor}")
    @GetResponses
    @Operation(summary = "Find investments by investor", description = "Lists all investments for a specific investor.")
    public ResponseEntity<List<InvestmentDTO>> findInvestmentsByInvestor(@Parameter @PathVariable UUID investor) {

        List<InvestmentDTO> investmentsByInvestor = investmentService.findInvestmentsByInvestor(investor);

        return new ResponseEntity<>(investmentsByInvestor, HttpStatus.OK);
    }

    @GetMapping("/assets/{asset}")
    @GetResponses
    @Operation(summary = "Find investments by asset", description = "Lists all investments for a specific asset.")
    public ResponseEntity<List<InvestmentDTO>> findInvestmentsByAsset(@Parameter @Valid @PathVariable String asset) {

        List<InvestmentDTO> investmentsDTO = investmentService.findInvestmentsByAsset(asset);

        return ResponseEntity.ok(investmentsDTO);
    }

    @PutMapping("/{id}")
    @PutResponses
    @Operation(summary = "Update an investment", description = "Updates an existing investment by its ID.")
    public ResponseEntity<InvestmentDTO> updateInvestment(@Parameter @PathVariable UUID id, @Valid @RequestBody InvestmentDTO dto) {

        InvestmentDTO updatedInvestment = investmentService.updateInvestment(id, dto);

        return ResponseEntity.ok(updatedInvestment);
    }

    @DeleteMapping("/{id}")
    @DeleteResponses
    @Operation(summary = "Delete an investment", description = "Deletes an investment by its ID.")
    public ResponseEntity<String> deleteInvestment(@Parameter @PathVariable UUID id) {

       investmentService.deleteInvestment(id);

        return ResponseEntity.noContent().build();
    }

}
