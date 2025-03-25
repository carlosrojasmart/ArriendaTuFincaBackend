package com.arriendatufinca.arriendatufinca.Services.Tenant;

import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Repositories.RatingRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RentalRequestRepository rentalRequestRepository;

    public Rating rateLandlord(Long requestId, int score, String comment) {
        RentalRequest request = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        Rating rating = new Rating();
        rating.setRequest(request);
        rating.setScore(score);
        rating.setComment(comment);
        rating.setType(RatingType.FOR_LANDLORD); // Calificación para el arrendador
        rating.setDate(LocalDateTime.now());

        return ratingRepository.save(rating);
    }
    public Rating rateTenant(Long requestId, int score, String comment) {
        RentalRequest request = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Rental request not found"));

        Rating rating = new Rating();
        rating.setRequest(request);
        rating.setScore(score);
        rating.setComment(comment);
        rating.setType(RatingType.FOR_TENANT); // Calificación para el TENANT
        rating.setDate(LocalDateTime.now());

        return ratingRepository.save(rating);
    }
}
