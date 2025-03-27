package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestResponseDTO;
import com.arriendatufinca.arriendatufinca.Entities.*;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalRequestControllerTest {

    private final RentalRequestService rentalRequestService = mock(RentalRequestService.class);
    private RentalRequestController rentalRequestController;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();

        // Configuración específica para mapear property.title a propertyTitle
        modelMapper.typeMap(RentalRequest.class, RentalRequestResponseDTO.class)
                .addMapping(src -> src.getProperty().getTitle(),
                        RentalRequestResponseDTO::setPropertyTitle);

        rentalRequestController = new RentalRequestController(rentalRequestService);
    }

    @Test
    public void getRentalRequestsForLandlord_ShouldReturnDTOList() {
        // Configuración de prueba
        Long landlordId = 1L;

        // Crear datos de ejemplo
        Property property = new Property();
        property.setTitle("Casa en la playa");

        RentalRequest request = new RentalRequest();
        request.setId(1L);
        request.setState(RequestState.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        request.setProperty(property);

        // Simular el servicio
        when(rentalRequestService.getRequestsForLandlord(landlordId))
                .thenReturn(List.of(request));

        // Llamar al método del controller
        ResponseEntity<List<RentalRequestResponseDTO>> response =
                rentalRequestController.getRentalRequestsForLandlord(landlordId);

        // Verificaciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        List<RentalRequestResponseDTO> responseDTOs = response.getBody();
        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());

        RentalRequestResponseDTO responseDTO = responseDTOs.get(0);
        assertEquals(1L, responseDTO.getId());
        assertEquals(RequestState.PENDING, responseDTO.getState());
        assertEquals("Casa en la playa", responseDTO.getPropertyTitle());
        assertNotNull(responseDTO.getCreatedAt());
    }
}