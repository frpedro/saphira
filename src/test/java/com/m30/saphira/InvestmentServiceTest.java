package com.m30.saphira;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.model.Investment;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestmentRepository;
import com.m30.saphira.repository.InvestorRepository;
import com.m30.saphira.service.InvestmentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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
    private Investor investor;

    @BeforeEach
    void setUp() {

        investorId = UUID.fromString("8a8a8a8a-8a8a-8a8a-8a8a-8a8a8a8a8a8a");

        investor = new Investor();
        investor.setId(investorId);

        investmentDTO = new InvestmentDTO(investorId, "MRFX-11", 150.00);

        investment = new Investment();
        investment.setInvestor(investor);
        investment.setAsset(investmentDTO.getAsset());
        investment.setAppliedValue(investmentDTO.getAppliedValue());

    }

    @Nested
    @DisplayName("CREATE: Create investment tests")
    class CreateInvestmentTests {

        @Test
        @DisplayName("Should create a investment when ID was found")
        void shouldCreateNewInvestment_whenInvestorWasFound() {

            // arrange
            when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
            when(investmentRepository.save(any(Investment.class))).thenReturn(investment);

            // act
            InvestmentDTO result = investmentService.createInvestment(investmentDTO);

            // assert
            Assertions.assertEquals(result.getAsset(), investmentDTO.getAsset());
            Assertions.assertEquals(result.getAppliedValue(), investmentDTO.getAppliedValue());

            verify(investorRepository, times(1)).findById(investorId);
            verify(investmentRepository, times(1)).save(any(Investment.class));

        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when investor not found")
        void shouldThrowResourceNotFoundException_whenInvestorNotFound() {

        }

    }





}
