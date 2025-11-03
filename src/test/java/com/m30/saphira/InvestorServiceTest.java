package com.m30.saphira;
import com.m30.saphira.dto.InvestorDTO;
import com.m30.saphira.exception.DataConflictException;
import com.m30.saphira.exception.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

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
    private UUID investorId;

    // defines a standard configuration for testing
    @BeforeEach
    void setUp() {

        investorId = UUID.fromString("8a8a8a8a-8a8a-8a8a-8a8a-8a8a8a8a8a8a");

        investorDto = new InvestorDTO("Pedro Fernandes", "pedro.fernandes@email.com", Investor.InvestorProfile.arrojado);

        investor = new Investor();

        investor.setId(investorId);
        investor.setName(investorDto.getName());
        investor.setEmail(investorDto.getEmail());
        investor.setInvestorProfile(investorDto.getInvestorProfile());
    }

    @Nested
    @DisplayName("CREATE: Investor creation tests")
    class CreateInvestorTests {

        @Test
        @DisplayName("Should create a new investor when email is not in use.")
        void shouldCreateNewInvestor_whenEmailIsNotInUse() {

            when(investorRepository.existsByEmail(investorDto.getEmail())).thenReturn(false);

            when(investorRepository.save(any(Investor.class))).thenReturn(investor);

            InvestorDTO result = investorService.createInvestor(investorDto.getInvestorProfile(), investorDto);

            assertNotNull(result);
            assertEquals(result.getName(), investorDto.getName());
            assertEquals(result.getEmail(), investorDto.getEmail());

            verify(investorRepository, times(1)).existsByEmail(investorDto.getEmail());
            verify(investorRepository).save(any(Investor.class));
        }

        @Test
        @DisplayName("Should throw a DataConflictException when creating a new investor with an email already in use.")
        void shouldThrowDataConflictException_whenEmailIsAlreadyInUse() {

            when(investorRepository.existsByEmail(investorDto.getEmail())).thenReturn(true);

            DataConflictException thrownException = assertThrows(DataConflictException.class, () ->
                    investorService.createInvestor(investorDto.getInvestorProfile(), investorDto));

            verify(investorRepository, never()).save(any(Investor.class));
        }

    }

    @Nested
    @DisplayName("READ: Investor search tests")
    class FindInvestorsTests {

        @Test
        @DisplayName("Should return all investors.")
        void shouldReturnAllInvestors() {

            List<Investor> mockInvestors = List.of(investor);
            when(investorRepository.findAll()).thenReturn(mockInvestors);

            List<InvestorDTO> result = investorService.findAllInvestors();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(investor.getName(), result.get(0).getName());

            verify(investorRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no investors exist.")
        void shouldReturnEmptyList_whenNoInvestorsExist() {

            when(investorRepository.findAll()).thenReturn(Collections.emptyList());

            List<InvestorDTO> result = investorService.findAllInvestors();

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(investorRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return a list of investors by profile.")
        void shouldReturnInvestorsListByProfile() {

            Investor.InvestorProfile profile = Investor.InvestorProfile.arrojado;

            when(investorRepository.findByInvestorProfile(profile))
                    .thenReturn(Collections.singletonList(investor));

            List<InvestorDTO> result = investorService.findInvestorByProfile(profile);

            assertNotNull(result);
            assertFalse(result.isEmpty());

            assertEquals(1, result.size());

            assertEquals(investor.getInvestorProfile(), result.get(0).getInvestorProfile());

            verify(investorRepository, times(1)).findByInvestorProfile(profile);
        }

        @Test
        @DisplayName("Should return empty list when no investors by profile exist.")
        void shouldReturnEmptyList_whenNoInvestorsByProfileExist() {

            Investor.InvestorProfile profile = Investor.InvestorProfile.arrojado;

            when(investorRepository.findByInvestorProfile(profile)).thenReturn(Collections.emptyList());

            List<InvestorDTO> result = investorService.findInvestorByProfile(profile);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(investorRepository, times(1)).findByInvestorProfile(profile);
        }

        @Test
        @DisplayName("Should return a investor by ID.")
        void shouldReturnInvestorById() {

            when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));

            InvestorDTO result = investorService.findInvestorsById(investorId);

            assertNotNull(result);

            assertEquals("Pedro Fernandes", result.getName(), "O nome do investidor não corresponde.");

            verify(investorRepository, times(1)).findById(investorId);
        }

        @Test
        @DisplayName("Should throw ReturnResourceNotFoundException when investor by ID not found.")
        void shouldThrowResourceNotFoundException_whenInvestorByIdNotFound() {

            UUID nonExistentId = UUID.fromString("1b1b1b1b-1b1b-1b1b-1b1b-1b1b1b1b1b1b");

            when(investorRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                investorService.findInvestorsById(nonExistentId);
            });

            verify(investorRepository, times(1)).findById(nonExistentId);
        }

        @Test
        @DisplayName("Should return a investor by name.")
        void shouldReturnInvestorByName() {

            when(investorRepository.findByName(investor.getName())).thenReturn(Optional.of(investor));

            InvestorDTO result = investorService.findInvestorsByName(investor.getName());

            assertNotNull(result);
            assertEquals(investor.getName(), result.getName());

            verify(investorRepository, times(1)).findByName(investor.getName());
        }
    }

    @Nested
    @DisplayName("UPDATE: Investor update tests")
    class UpdateInvestorTests {

        @Test
        @DisplayName("Should update investor by ID.")
        void shouldUpdateInvestorByID() {

            when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));

            when(investorRepository.save(any(Investor.class))).thenReturn(investor);

            InvestorDTO result = investorService.updateInvestor(investorId, investorDto);

            verify(investorRepository, times(1)).findById(investorId);
            verify(investorRepository, times(1)).save(any(Investor.class));

            assertNotNull(result);
            assertEquals(investor.getName(), result.getName());
            assertEquals(investor.getEmail(), result.getEmail());
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when trying update investor by ID not found.")
        void shouldThrowResourceNotFoundException_whenInvestorNotFound() {

            UUID nonExistentId = UUID.fromString("1b1b1b1b-1b1b-1b1b-1b1b-1b1b1b1b1b1b");

            when(investorRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                investorService.updateInvestor(nonExistentId, investorDto);
            });

            verify(investorRepository, times(1)).findById(nonExistentId);
            verify(investorRepository, never()).save(any(Investor.class));
        }

        @Test
        @DisplayName("Should throw a DataConflictException when updating a new investor with an email already in use.")
        void shouldThrowDataConflictException_whenEmailIsAlreadyInUse() {

            Investor existingInvestor = new Investor();
            existingInvestor.setEmail("email.antigo@example.com");

            when(investorRepository.findById(investorId)).thenReturn(Optional.of(existingInvestor));

            when(investorRepository.existsByEmail(investorDto.getEmail())).thenReturn(true);

            DataConflictException thrownException = assertThrows(DataConflictException.class, () ->
                    investorService.updateInvestor(investorId, investorDto));

            verify(investorRepository, never()).save(any(Investor.class));
            verify(investorRepository, times(1)).findById(investorId);
            verify(investorRepository, times(1)).existsByEmail(investorDto.getEmail());
        }
    }

    @Nested
    @DisplayName("DELETE: Investor delete tests")
    class DeleteInvestorTests {

        @Test
        @DisplayName("Should delete investor by ID.")
        void shouldDeleteInvestorByID() {

            when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));

            doNothing().when(investorRepository).delete(any(Investor.class));

            investorService.deleteInvestor(investorId);

            verify(investorRepository, times(1)).findById(investorId);
            verify(investorRepository, times(1)).delete(investor);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when trying delete investor by ID not found.")
        void shouldThrowResourceNotFoundException_whenInvestorNotFound() {

            UUID nonExistentId = UUID.fromString("1b1b1b1b-1b1b-1b1b-1b1b-1b1b1b1b1b1b");

            when(investorRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                investorService.deleteInvestor(nonExistentId);
            });

            verify(investorRepository, times(1)).findById(nonExistentId);
            verify(investorRepository, never()).delete(any(Investor.class));
        }
    }

}
