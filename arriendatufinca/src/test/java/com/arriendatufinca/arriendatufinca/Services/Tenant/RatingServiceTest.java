package com.arriendatufinca.arriendatufinca.Services.Tenant;

import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.RatingRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RentalRequestRepository rentalRequestRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void rateLandlord_ValidRequest_ReturnsRating() {
        // Arrange
        RentalRequest request = new RentalRequest();
        request.setId(1L);
        request.setTenant(new User(1L, "tenant_user", "Tenant", "User", "tenant@example.com", StatusEnum.ACTIVE, null, null, null));

        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(ratingRepository.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Rating rating = ratingService.rateLandlord(1L, 5, "Excelente arrendador");

        // Assert
        assertNotNull(rating);
        assertEquals(5, rating.getScore());
        assertEquals("Excelente arrendador", rating.getComment());
        assertEquals(RatingType.FOR_LANDLORD, rating.getType());
        assertEquals(request, rating.getRequest());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void rateLandlord_InvalidRequest_ThrowsException() {
        // Arrange
        when(rentalRequestRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                ratingService.rateLandlord(999L, 5, "Excelente arrendador")
        );
    }
}