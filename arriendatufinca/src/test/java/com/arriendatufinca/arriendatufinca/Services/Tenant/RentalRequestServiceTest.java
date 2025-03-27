package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestDTO;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class RentalRequestServiceTest {
    @Mock
    private RentalRequestRepository rentalRequestRepository;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RentalRequestService rentalRequestService;

    private RentalRequest rentalRequest;
    private RentalRequestDTO rentalRequestDTO;
    private User tenant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        tenant = new User();
        tenant.setId(1L);
        
        rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);
        rentalRequest.setTenant(tenant);
        rentalRequest.setCreatedAt(LocalDateTime.now());
        rentalRequest.setStatus(StatusEnum.ACTIVE);
        rentalRequest.setState(RequestState.PENDING);
        
        rentalRequestDTO = new RentalRequestDTO();
        rentalRequestDTO.setId(1L);
        rentalRequestDTO.setStatus(StatusEnum.ACTIVE);
        rentalRequestDTO.setState(RequestState.PENDING);
    }

    @Test
    void testCreateRentalRequest() {
        when(modelMapper.map(any(RentalRequestDTO.class), eq(RentalRequest.class))).thenReturn(rentalRequest);
        when(rentalRequestRepository.save(any(RentalRequest.class))).thenReturn(rentalRequest);
        when(modelMapper.map(any(RentalRequest.class), eq(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);

        RentalRequestDTO result = rentalRequestService.createRentalRequest(rentalRequestDTO);

        assertNotNull(result);
        assertEquals(RequestState.PENDING, result.getState());
        verify(rentalRequestRepository).save(any(RentalRequest.class));
    }

    @Test
    void testGetRequestsForCurrentTenant() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(tenant));
        when(rentalRequestRepository.findAllForTenant(tenant)).thenReturn(List.of(rentalRequest));
        when(modelMapper.map(any(RentalRequest.class), eq(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);

        List<RentalRequestDTO> result = rentalRequestService.getRequestsForCurrentTenant(1L);

        assertEquals(1, result.size());
        verify(rentalRequestRepository).findAllForTenant(tenant);
    }

    @Test
    void testGetRequestsForLandlord() {
        when(rentalRequestRepository.findByPropertyLandlordId(1L)).thenReturn(List.of(rentalRequest));
        when(modelMapper.map(any(RentalRequest.class), eq(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);

        List<RentalRequestDTO> result = rentalRequestService.getRequestsForLandlord(1L);

        assertEquals(1, result.size());
        verify(rentalRequestRepository).findByPropertyLandlordId(1L);
    }

    @Test
    void testApproveRentalRequest() {
        // Simular una solicitud de arriendo en estado PENDING
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);
        rentalRequest.setState(RequestState.PENDING);
    
        // Simular la respuesta del repositorio cuando se busca por ID
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
    
        // Simular que el repositorio devuelve la solicitud con estado APPROVED después de guardar
        when(rentalRequestRepository.save(any(RentalRequest.class)))
            .thenAnswer(invocation -> {
                RentalRequest savedRequest = invocation.getArgument(0);
                savedRequest.setState(RequestState.APPROVED);
                return savedRequest;
            });
    
        // Simular la conversión con modelMapper
        RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
        rentalRequestDTO.setState(RequestState.APPROVED);
    
        when(modelMapper.map(any(RentalRequest.class), eq(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);
    
        // Ejecutar el método
        RentalRequestDTO result = rentalRequestService.approveRentalRequest(1L);
    
        // Verificar que el resultado no sea null
        assertNotNull(result, "El resultado no debería ser null");
    
        // Verificar que el estado cambió a APPROVED
        assertEquals(RequestState.APPROVED, result.getState(), "El estado no se actualizó correctamente");
    
        // Verificar interacciones con los mocks
        verify(rentalRequestRepository).findById(1L);
        verify(rentalRequestRepository).save(any(RentalRequest.class));
        verify(modelMapper).map(any(RentalRequest.class), eq(RentalRequestDTO.class));
    }
    

    @Test
    void testRejectRentalRequest() {
        // Simular una solicitud de arriendo en estado PENDING
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);
        rentalRequest.setState(RequestState.PENDING);
    
        // Simular la respuesta del repositorio cuando se busca por ID
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
    
        // Simular que el repositorio devuelve la solicitud con estado REJECTED después de guardar
        when(rentalRequestRepository.save(any(RentalRequest.class)))
            .thenAnswer(invocation -> {
                RentalRequest savedRequest = invocation.getArgument(0);
                savedRequest.setState(RequestState.REJECTED);
                return savedRequest;
            });
    
        // Simular la conversión con modelMapper
        RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
        rentalRequestDTO.setState(RequestState.REJECTED);
    
        when(modelMapper.map(any(RentalRequest.class), eq(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);
    
        // Ejecutar el método
        RentalRequestDTO result = rentalRequestService.rejectRentalRequest(1L);
    
        // Verificar que el resultado no sea null
        assertNotNull(result, "El resultado no debería ser null");
    
        // Verificar que el estado cambió a REJECTED
        assertEquals(RequestState.REJECTED, result.getState(), "El estado no se actualizó correctamente");
    
        // Verificar interacciones con los mocks
        verify(rentalRequestRepository).findById(1L);
        verify(rentalRequestRepository).save(any(RentalRequest.class));
        verify(modelMapper).map(any(RentalRequest.class), eq(RentalRequestDTO.class));
    }
}
