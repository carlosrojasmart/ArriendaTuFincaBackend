package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.arriendatufinca.arriendatufinca.DTO.RatingDTO;
import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.RatingRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RentalRequestRepository rentalRequestRepository;

    @InjectMocks
    private RatingService ratingService;

    private RentalRequest rentalRequest;
    private RatingDTO ratingDTO;
    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);

        ratingDTO = new RatingDTO(1L, 5, "Excelente experiencia");

        rating = new Rating();
        rating.setId(1L);
        rating.setRequest(rentalRequest);
        rating.setScore(5);
        rating.setComment("Excelente experiencia");
        rating.setType(RatingType.FOR_LANDLORD);
        rating.setStatus(StatusEnum.ACTIVE);
    }

    @Test
    void testRateLandlord() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        RatingDTO result = ratingService.rateLandlord(ratingDTO);

        assertNotNull(result);
        assertEquals(1L, result.getRequestId());
        assertEquals(5, result.getScore());
        assertEquals("Excelente experiencia", result.getComment());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testRateTenant() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        RatingDTO result = ratingService.rateTenant(ratingDTO);

        assertNotNull(result);
        assertEquals(1L, result.getRequestId());
        assertEquals(5, result.getScore());
        assertEquals("Excelente experiencia", result.getComment());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testRateProperty() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        RatingDTO result = ratingService.rateProperty(ratingDTO);

        assertNotNull(result);
        assertEquals(1L, result.getRequestId());
        assertEquals(5, result.getScore());
        assertEquals("Excelente experiencia", result.getComment());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testRateLandlordThrowsExceptionWhenRequestNotFound() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.rateLandlord(ratingDTO);
        });

        assertEquals("Rental request not found", exception.getMessage());
        verify(ratingRepository, never()).save(any(Rating.class));
    }
}
