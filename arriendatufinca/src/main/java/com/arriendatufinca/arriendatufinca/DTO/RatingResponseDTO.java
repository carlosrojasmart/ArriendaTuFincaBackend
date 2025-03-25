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

    // Campo para el ID de la solicitud
    private Long requestId;

    // Configuración para el mapeo automático
    public void setRequestIdFromRequest(Long requestId) {
        this.requestId = requestId;
    }

    // O si prefieres, puedes hacerlo así:
    public void setRequest_Id(Long requestId) {
        this.requestId = requestId;
    }
}