package com.arriendatufinca.arriendatufinca.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/rate-landlord")
    public ResponseEntity<Rating> rateLandlord(
            @RequestParam Long requestId,
            @RequestParam int score,
            @RequestParam String comment
    ) {
        Rating rating = ratingService.rateLandlord(requestId, score, comment);
        return ResponseEntity.ok(rating);
    }
    @PostMapping("/rental-requests/{id}/rate-tenant")
    public ResponseEntity<Rating> rateTenant(
            @RequestParam Long requestid,
            @RequestParam int score,
            @RequestParam(required = false) String comment) {
        Rating rating = ratingService.rateTenant(requestid, score, comment);
        return ResponseEntity.ok(rating);
    }
@PostMapping("/rental-requests/{id}/rate-property")
public ResponseEntity<Rating> rateProperty(
        @RequestParam Long requestid,
        @RequestParam int score,
        @RequestParam(required = false) String comment) {
    Rating rating = ratingService.rateProperty(requestid, score, comment);
    return ResponseEntity.ok(rating);
        }
}
