package com.m30.saphira;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.model.Investment;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import com.m30.saphira.service.InvestmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class InvestmentServiceTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestmentService investmentService;

    private InvestmentDTO investmentDTO;
    private Investment investment;
    private UUID investorId;

    @BeforeEach
    void setUp() {

        investorId = UUID.fromString("8a8a8a8a-8a8a-8a8a-8a8a-8a8a8a8a8a8a");

        investmentDTO = new InvestmentDTO(investorId, "MRFX-11", 150.00);

        investmentDTO.setInvestorId(investorId);
        investmentDTO.setAsset(investmentDTO.getAsset());
        investmentDTO.setAppliedValue(investmentDTO.getAppliedValue());

    }

    @Nested
    @DisplayName("CREATE: Create investment tests")
    class CreateInvestmentTests {

        @Test
        @DisplayName("Should create a investment when ID was found")
        void shouldCreateNewInvestment_whenInvestorWasFound() {

            // when(investorRepository.findById(investorId).(Optional.of());

            // when(investmentRepository.save(any(Investment.class))).thenAnswer();

            // act
            InvestmentDTO result = investmentService.createInvestment(investmentDTO);



        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when investor not found")
        void shouldThrowResourceNotFoundException_whenInvestorNotFound() {

        }

    }





}
