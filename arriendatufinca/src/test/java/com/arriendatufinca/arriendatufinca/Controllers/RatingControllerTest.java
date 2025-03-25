package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.RatingResponseDTO;
import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RatingService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RatingControllerTest {

    private final RatingService ratingService = mock(RatingService.class);
    private final RatingController ratingController = new RatingController(ratingService);
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void rateLandlordTest() {
        // Configuración de datos de prueba
        Long requestId = 1L;
        int score = 4;
        String comment = "Excelente arrendador, muy atento";

        // Crear objeto RentalRequest simulado
        RentalRequest mockRequest = new RentalRequest();
        mockRequest.setId(requestId);

        // Crear objeto Rating esperado
        Rating expectedRating = new Rating();
        expectedRating.setId(1L);
        expectedRating.setRequest(mockRequest); // Establecemos el objeto request completo
        expectedRating.setScore(score);
        expectedRating.setComment(comment);
        expectedRating.setType(RatingType.FOR_LANDLORD);
        expectedRating.setStatus(StatusEnum.ACTIVE);
        expectedRating.setDate(LocalDateTime.now());

        // Mock del servicio
        when(ratingService.rateLandlord(requestId, score, comment)).thenReturn(expectedRating);

        // Llamada al controlador
        ResponseEntity<Rating> response = ratingController.rateLandlord(requestId, score, comment);
        Rating rating = response.getBody();
        RatingResponseDTO responseDTO = modelMapper.map(rating, RatingResponseDTO.class);

        // Aserciones
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(responseDTO);
        assertEquals(requestId, responseDTO.getRequestId()); // Esto funcionará si el ModelMapper puede mapear request.id a requestId
        assertEquals(score, responseDTO.getScore());
        assertEquals(comment, responseDTO.getComment());
        assertEquals(RatingType.FOR_LANDLORD, responseDTO.getType());
        assertNotNull(responseDTO.getDate());
    }
}