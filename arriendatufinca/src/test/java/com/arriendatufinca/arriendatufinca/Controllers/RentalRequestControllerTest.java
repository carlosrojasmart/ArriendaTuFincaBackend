package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestResponseDTO;
import com.arriendatufinca.arriendatufinca.Entities.*;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalRequestControllerTest {

    private final RentalRequestService rentalRequestService = mock(RentalRequestService.class);
    private final RentalRequestController rentalRequestController = new RentalRequestController(rentalRequestService);

    @Test
    public void getRentalRequestsForLandlord_ShouldReturnDTOList() {
        try {
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

            // Mapeo manual a DTO (sin ModelMapper)
            RentalRequestResponseDTO responseDTO = new RentalRequestResponseDTO();
            responseDTO.setId(request.getId());
            responseDTO.setState(request.getState());
            responseDTO.setCreatedAt(request.getCreatedAt());
            responseDTO.setPropertyTitle(request.getProperty().getTitle());

            // Verificaciones
            assertEquals(1, response.getBody().size());
            assertEquals(1L, responseDTO.getId());
            assertEquals(RequestState.PENDING, responseDTO.getState());
            assertEquals("Casa en la playa", responseDTO.getPropertyTitle());

        } catch (Exception e) {
            fail("La prueba falló con la excepción: " + e.getMessage());
        }
    }
}