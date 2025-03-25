package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.DTO.RatingDTO;
import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.RatingRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RentalRequestRepository rentalRequestRepository;

    public RatingDTO rateLandlord(RatingDTO ratingDTO) {
        RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        Rating rating = ratingRepository.save(mapToEntity(ratingDTO, request, RatingType.FOR_LANDLORD));
        return mapToDTO(rating);
    }

    public RatingDTO rateTenant(RatingDTO ratingDTO) {
        RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        Rating rating = ratingRepository.save(mapToEntity(ratingDTO, request, RatingType.FOR_TENANT));
        return mapToDTO(rating);
    }

    public RatingDTO rateProperty(RatingDTO ratingDTO) {
        RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        Rating rating = ratingRepository.save(mapToEntity(ratingDTO, request, RatingType.FOR_PROPERTY));
        return mapToDTO(rating);
    }

    private Rating mapToEntity(RatingDTO ratingDTO, RentalRequest request, RatingType type) {
        Rating rating = new Rating();
        rating.setRequest(request);
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        rating.setType(type);
        rating.setDate(LocalDateTime.now());
        rating.setStatus(StatusEnum.ACTIVE);
        return rating;
    }

    private RatingDTO mapToDTO(Rating rating) {
        return new RatingDTO(

                rating.getRequest().getId(),
                rating.getScore(),
                rating.getComment()
                
        );
    }
}
