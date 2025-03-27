package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    RentalRequestRepository rentalRequestRepository;
    @Autowired
    ModelMapper modelMapper;


    public RatingDTO rateLandlord(RatingDTO ratingDTO) {
         RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Rental request not found"));
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setRequest(request);
        rating.setType(RatingType.FOR_LANDLORD);
        rating.setDate(LocalDateTime.now());
        rating.setStatus(StatusEnum.ACTIVE);
        return modelMapper.map(ratingRepository.save(rating), RatingDTO.class);
    }

    public RatingDTO rateTenant(RatingDTO ratingDTO) {
        RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
        .orElseThrow(() -> new RuntimeException("Rental request not found"));
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setRequest(request);
        rating.setType(RatingType.FOR_TENANT);
        rating.setDate(LocalDateTime.now());
        rating.setStatus(StatusEnum.ACTIVE);
        return modelMapper.map(ratingRepository.save(rating), RatingDTO.class);
    }

    public RatingDTO rateProperty(RatingDTO ratingDTO) {
        RentalRequest request = rentalRequestRepository.findById(ratingDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Rental request not found"));
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setRequest(request);
        rating.setType(RatingType.FOR_PROPERTY);
        rating.setDate(LocalDateTime.now());
        rating.setStatus(StatusEnum.ACTIVE);
        return modelMapper.map(ratingRepository.save(rating), RatingDTO.class);
    }
}
