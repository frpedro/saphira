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

@RequiredArgsConstructor
@RestController
@RequestMapping("/investors" )
@Tag(name = "Investors", description = "Endpoints for managing investor data.")
public class InvestorController {

    private final InvestorService investorService;

    @PostMapping
    @PostResponses
    @Operation(
            summary = "Create a new investor",
            description = "Registers a new investor in the system. The email provided must be unique."
    )
    public ResponseEntity<InvestorDTO> createInvestor(@Parameter @Valid @RequestBody InvestorDTO dto) {
        InvestorDTO newInvestor = investorService.createInvestor(dto.getInvestorProfile(), dto);
        return new ResponseEntity<>(newInvestor, HttpStatus.CREATED);
    }

    @GetMapping
    @GetResponses
    @Operation(
            summary = "List all investors",
            description = "Retrieves a complete list of all investors currently registered in the system."
    )
    public ResponseEntity<List<InvestorDTO>> listAllInvestors() {
        List<InvestorDTO> allInvestors = investorService.findAllInvestors();
        return new ResponseEntity<>(allInvestors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @GetResponses
    @Operation(
            summary = "Find an investor by ID",
            description = "Retrieves the details of a single investor using their unique UUID as the identifier."
    )
    public ResponseEntity<InvestorDTO> findInvestorById(@Parameter @Valid @PathVariable UUID id) {
        InvestorDTO investor = investorService.findInvestorsById(id);
        return ResponseEntity.ok(investor);
    }

    @GetMapping("/name/{name}")
    @GetResponses
    @Operation(
            summary = "Find an investor by name",
            description = "Searches for and retrieves an investor by their full name. The search is case-sensitive."
    )
    public ResponseEntity<InvestorDTO> findInvestorByName(@Parameter @Valid @PathVariable String name) {
        InvestorDTO investor = investorService.findInvestorsByName(name);
        return ResponseEntity.ok(investor);
    }

    @GetMapping("/profile/{profile}")
    @GetResponses
    @Operation(
            summary = "List investors by profile",
            description = "Retrieves a list of all investors that match a specific investment profile (e.g., CONSERVADOR, MODERADO, ARROJADO)."
    )
    public ResponseEntity<List<InvestorDTO>> findInvestorByProfile(@Parameter @Valid @PathVariable Investor.InvestorProfile profile) {
        List<InvestorDTO> investors = investorService.findInvestorByProfile(profile);
        return ResponseEntity.ok(investors);
    }

    @PutMapping("/{id}")
    @PutResponses
    @Operation(
            summary = "Update an investor",
            description = "Updates the information of an existing investor, identified by their UUID. If the email is changed, it must remain unique in the system."
    )
    public ResponseEntity<InvestorDTO> updateInvestor(@Parameter @PathVariable UUID id, @Valid @RequestBody InvestorDTO investorDTO) {
        InvestorDTO updatedInvestor = investorService.updateInvestor(id, investorDTO);
        return ResponseEntity.ok(updatedInvestor);
    }

    @DeleteMapping("/{id}")
    @DeleteResponses
    @Operation(
            summary = "Delete an investor",
            description = "Permanently deletes an investor from the system using their UUID. This action cannot be undone."
    )
    public ResponseEntity<String> deleteInvestor(@Parameter @Valid @PathVariable UUID id) {
        investorService.deleteInvestor(id);
        return ResponseEntity.noContent().build();
    }
}
