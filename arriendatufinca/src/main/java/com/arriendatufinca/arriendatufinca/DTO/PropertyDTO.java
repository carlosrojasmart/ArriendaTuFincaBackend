package com.arriendatufinca.arriendatufinca.DTO;

import lombok.Data;

@Data
public class PropertyDTO {
    private Long landlordId;
    private String title;
    private String description;
    private int bathrooms;
    private int bedrooms;
    private double area;
    private String city;
    private String address;
    private double price;
}
