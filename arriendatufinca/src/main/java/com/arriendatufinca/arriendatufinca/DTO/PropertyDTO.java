package com.arriendatufinca.arriendatufinca.DTO;

import com.arriendatufinca.arriendatufinca.Enums.PropertyState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {

    private Long id;
    private Long landlordId;
    private String title;
    private String description;
    private int bathrooms;
    private int bedrooms;
    private double area;
    private String city;
    private String address;
    private double price;
    private PropertyState status;
}
