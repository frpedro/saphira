package com.m30.saphira;
import com.m30.saphira.dto.InvestmentDTO;
import com.m30.saphira.exception.ResourceNotFoundException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    private UUID investmentId;

    // defines a standard configuration for testing
    @BeforeEach
    void setUp() {

        investorId = UUID.fromString("8a8a8a8a-8a8a-8a8a-8a8a-8a8a8a8a8a8a");

        investmentId = UUID.fromString("c7a0f498-9d52-47a0-95f3-2c3e6b4e2f18");

        investor = new Investor();
        investor.setId(investorId);

        investmentDTO = new InvestmentDTO(investorId, "MRFX-11", 150.00);

        investment = new Investment();
        investment.setId(investmentId);
        investment.setInvestor(investor);
        investment.setAsset(investmentDTO.getAsset());
        investment.setAppliedValue(investmentDTO.getAppliedValue());
    }

    @Nested
    @DisplayName("CREATE: Create investment tests")
    class CreateInvestmentTests {

        @Test
        @DisplayName("Should create a investment when ID was found.")
        void shouldCreateNewInvestment_whenInvestorWasFound() {

            when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
            when(investmentRepository.save(any(Investment.class))).thenReturn(investment);

            InvestmentDTO result = investmentService.createInvestment(investmentDTO);

            Assertions.assertEquals(investmentDTO.getAsset(), result.getAsset());
            Assertions.assertEquals(investmentDTO.getAppliedValue(), result.getAppliedValue());
            Assertions.assertNotNull(result);

            verify(investorRepository, times(1)).findById(investorId);
            verify(investmentRepository, times(1)).save(any(Investment.class));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when investor not found.")
        void shouldThrowResourceNotFoundException_whenInvestorNotFound() {

            when(investorRepository.findById(investorId)).thenReturn(Optional.empty());

            Assertions.assertThrows(ResourceNotFoundException.class,() ->
                    investmentService.createInvestment(investmentDTO));

            verify(investorRepository, times(1)).findById(investorId);
            verify(investmentRepository, never()).save(any(Investment.class));
        }
    }

    @Nested
    @DisplayName("READ: Investment search tests")
    class FindInvestmentTests {

        @Test
        @DisplayName("Should return all investments.")
        void shouldReturnAllInvestments() {

            List<Investment> mockInvestments = List.of(investment);
            when(investmentRepository.findAll()).thenReturn(mockInvestments);

            List<InvestmentDTO> result = investmentService.listAllInvestments();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(investment.getAppliedValue(), result.get(0).getAppliedValue());

            verify(investmentRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no investments exist.")
        void shouldReturnEmptyList_whenNoInvestmentsExist() {

            when(investmentRepository.findAll()).thenReturn(Collections.emptyList());

            List<InvestmentDTO> result = investmentService.listAllInvestments();

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(investmentRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return investment by investor.")
        void shouldReturnInvestmentByInvestor() {

            when(investmentRepository.findByInvestor_Id(investorId)).thenReturn(List.of(investment));

            List<InvestmentDTO> result = investmentService.findInvestmentsByInvestor(investorId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(investment.getAsset(), result.get(0).getAsset());

            verify(investmentRepository, times(1)).findByInvestor_Id(investorId);
        }

        @Test
        @DisplayName("Should return empty list when investments by investor not found.")
        void shouldReturnEmptyList_whenInvestmentsByInvestorNotFound() {

            when(investmentRepository.findByInvestor_Id(investorId)).thenReturn(Collections.emptyList());

            List<InvestmentDTO> result = investmentService.findInvestmentsByInvestor(investorId);

            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty());

            verify(investmentRepository, times(1)).findByInvestor_Id(investorId);
        }

        @Test
        @DisplayName("Should return investment by asset.")
        void shouldReturnInvestmentByAsset() {

            when(investmentRepository.findByAsset(investment.getAsset())).thenReturn(List.of(investment));

            List<InvestmentDTO> result = investmentService.findInvestmentsByAsset(investment.getAsset());

            Assertions.assertNotNull(result);
            Assertions.assertEquals(investment.getAsset(), result.get(0).getAsset());

            verify(investmentRepository, times(1)).findByAsset(investment.getAsset());
        }

        @Test
        @DisplayName("Should return empty list when investments by asset not found.")
        void shouldReturnEmptyList_whenInvestmentsByAssetNotFound() {

            when(investmentRepository.findByAsset(investment.getAsset())).thenReturn(Collections.emptyList());

            List<InvestmentDTO> result = investmentService.findInvestmentsByAsset(investment.getAsset());

            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty());

            verify(investmentRepository, times(1)).findByAsset(investment.getAsset());
        }
    }
    @Nested
    @DisplayName("UPDATE: Investment update tests")
    class InvestmentUpdateTests {

        @Test
        @DisplayName("Should update investment by ID.")
        void shouldUpdateInvestmentByID() {

            when(investmentRepository.findById(investment.getId())).thenReturn(Optional.of(investment));

            when(investmentRepository.save(any(Investment.class))).thenReturn(investment);

            InvestmentDTO result = investmentService.updateInvestment(investment.getId(), investmentDTO);

            assertNotNull(result);
            assertEquals(investment.getAsset(), result.getAsset());
            assertEquals(investment.getAppliedValue(), result.getAppliedValue());

            verify(investmentRepository, times(1)).findById(investment.getId());
            verify(investmentRepository, times(1)).save(any(Investment.class));
        }

        @Test
        @DisplayName("Should throw NotFoundException when investment not found.")
        void shouldThrowResourceNotFoundException_whenInvestmentNotFound() {

            when(investmentRepository.findById(investment.getId())).thenReturn(Optional.empty());

            Assertions.assertThrows(ResourceNotFoundException.class,() ->
                    investmentService.updateInvestment(investment.getId(), investmentDTO));

            verify(investmentRepository, times(1)).findById(investment.getId());
            verify(investmentRepository, never()).save(any(Investment.class));
        }

    }
    @Nested
    @DisplayName("DELETE: Investment delete tests")
    class InvestmentDeleteTests {

        @Test
        @DisplayName("Should delete investment.")
        void shouldDeleteInvestment() {

            when(investmentRepository.findById(investment.getId())).thenReturn(Optional.of(investment));

            investmentService.deleteInvestment(investment.getId());

            verify(investmentRepository, times(1)).findById(investment.getId());
            verify(investmentRepository, times(1)).deleteById(investmentId);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when investment not found.")
        void shouldThrowResourceNotFoundException_whenInvestmentNotFound() {

            when(investmentRepository.findById(investment.getId())).thenReturn(Optional.empty());

            Assertions.assertThrows(ResourceNotFoundException.class, () -> {
                investmentService.deleteInvestment(investment.getId());
            });

            verify(investmentRepository, times(1)).findById(investment.getId());
            verify(investmentRepository, never()).delete(any(Investment.class));
        }
    }
}
