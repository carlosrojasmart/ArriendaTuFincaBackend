package com.arriendatufinca.arriendatufinca.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.DTO.RatingDTO;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/landlord")
    public ResponseEntity<RatingDTO> rateLandlord(@RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.rateLandlord(ratingDTO);
        return ResponseEntity.ok(savedRating);
    }

    @PostMapping("/tenant")
    public ResponseEntity<RatingDTO> rateTenant(@RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.rateTenant(ratingDTO);
        return ResponseEntity.ok(savedRating);
    }

    @PostMapping("/property")
    public ResponseEntity<RatingDTO> rateProperty(@RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.rateProperty(ratingDTO);
        return ResponseEntity.ok(savedRating);
    }
}
