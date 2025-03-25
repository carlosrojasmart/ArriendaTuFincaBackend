package com.arriendatufinca.arriendatufinca.DTO;

import com.arriendatufinca.arriendatufinca.Enums.PropertyState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyResponseDTO {
    private Long id;
    private String title;
    private PropertyState state;
}