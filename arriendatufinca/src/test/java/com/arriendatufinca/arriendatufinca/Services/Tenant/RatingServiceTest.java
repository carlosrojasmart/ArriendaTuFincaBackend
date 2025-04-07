package com.arriendatufinca.arriendatufinca.Services.Tenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.modelmapper.ModelMapper;

import com.arriendatufinca.arriendatufinca.DTO.RatingDTO;
import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Repositories.RatingRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RentalRequestRepository rentalRequestRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RatingService ratingService;

    private RentalRequest rentalRequest;
    private RatingDTO ratingDTO;
    private Rating rating;

    @BeforeEach
    void setUp() {
        ratingDTO = new RatingDTO(1L, 5, "Great landlord");

        rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);

        rating = new Rating();
        rating.setRequest(rentalRequest);
        rating.setScore(5);
        rating.setComment("Great landlord");
    }

    @Test
    void rateLandlord_ShouldSaveRating() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(modelMapper.map(any(RatingDTO.class), eq(Rating.class))).thenReturn(rating);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(modelMapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(ratingDTO);

        RatingDTO result = ratingService.rateLandlord(ratingDTO);

        assertNotNull(result);
        assertEquals(5, result.getScore());
        assertEquals("Great landlord", result.getComment());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void rateTenant_ShouldSaveRating() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(modelMapper.map(any(RatingDTO.class), eq(Rating.class))).thenReturn(rating);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(modelMapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(ratingDTO);

        RatingDTO result = ratingService.rateTenant(ratingDTO);

        assertNotNull(result);
        assertEquals(5, result.getScore());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void rateProperty_ShouldSaveRating() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(modelMapper.map(any(RatingDTO.class), eq(Rating.class))).thenReturn(rating);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        when(modelMapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(ratingDTO);

        RatingDTO result = ratingService.rateProperty(ratingDTO);

        assertNotNull(result);
        assertEquals(5, result.getScore());
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void rateLandlord_ShouldThrowException_WhenRentalRequestNotFound() {
        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ratingService.rateLandlord(ratingDTO));
    }
}
