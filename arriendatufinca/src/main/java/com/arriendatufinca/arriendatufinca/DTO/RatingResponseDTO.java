package com.arriendatufinca.arriendatufinca.DTO;

import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RatingResponseDTO {
    private Long id;
    private int score;
    private String comment;
    private LocalDateTime date;
    private RatingType type;

}