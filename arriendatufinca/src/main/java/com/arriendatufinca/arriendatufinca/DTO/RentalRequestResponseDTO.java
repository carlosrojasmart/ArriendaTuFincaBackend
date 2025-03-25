package com.arriendatufinca.arriendatufinca.DTO;

import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentalRequestResponseDTO {
    private Long id;
    private RequestState state;
    private LocalDateTime createdAt;
    private String propertyTitle;
}