package com.arriendatufinca.arriendatufinca.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    
    private Long requestId; // ID de la solicitud de arriendo
    private int score; // Puntuación de la calificación 
    private String comment; // Comentario de la calificación 

}
