package com.m30.saphira;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.DataConflictException;
import com.m30.saphira.model.Investor;
import com.m30.saphira.repository.InvestorRepository;
import com.m30.saphira.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestorService investorService;

    private InvestorDTO investorDto;
    private Investor investor;

    // defines a standard configuration for testing
    @BeforeEach
    void setUp() {

        investorDto = new InvestorDTO("Pedro Fernandes", "pedro.fernandes@email.com", Investor.InvestorProfile.arrojado);

        investor = new Investor();
        investor.setName(investorDto.getName());
        investor.setEmail(investorDto.getEmail());
        investor.setInvestorProfile(investorDto.getInvestorProfile());
    }

    @Nested
    @DisplayName("Investor creation tests")
    class createInvestorTests {

        @Test
        @DisplayName("Should create a new investor when email is not in use.")
        void shouldCreateNewInvestor_whenEmailIsNotInUse() {

            when(investorRepository.existsByEmail(investorDto.getEmail())).thenReturn(false);

            when(investorRepository.save(any(Investor.class))).thenReturn(investor);

            InvestorDTO result = investorService.createInvestor(investorDto.getInvestorProfile(), investorDto);

            assertNotNull(result);
            assertEquals(result.getName(), investorDto.getName());
            assertEquals(result.getEmail(), investorDto.getEmail());

            verify(investorRepository).existsByEmail(investorDto.getEmail());
            verify(investorRepository).save(any(Investor.class));
        }

        @Test
        @DisplayName("Should throw a DataConflictException when creating a new investor with an email already in use")
        void shouldThrowDataConflictException_whenEmailIsAlreadyInUse() {

            when(investorRepository.existsByEmail(investorDto.getEmail())).thenReturn(true);

            DataConflictException thrownException = assertThrows(DataConflictException.class, () ->
                    investorService.createInvestor(investorDto.getInvestorProfile(), investorDto));

            verify(investorRepository, never()).save(any(Investor.class));
        }

    }

    @Nested
    @DisplayName("Investor search tests")
    class FindInvestorsTests {

        @Test
        @DisplayName("Should return all investors")
        void shouldReturnAllInvestors() {

            List<Investor> mockInvestor = List.of(investor);
            when(investorRepository.findAll()).thenReturn(mockInvestor);

            List<InvestorDTO> result = investorService.findAllInvestors();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(investor.getName(), result.get(0).getName());

            verify(investorRepository, times(1)).findAll();

        }

        @Test
        @DisplayName("Should return empty list when no investors exist")
        void shouldReturnEmptyList_whenNoInvestorsExist() {

            when(investorRepository.findAll()).thenReturn(Collections.emptyList());

            List<InvestorDTO> result = investorService.findAllInvestors();

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(investorRepository, times(1)).findAll();
        }
    }

}
