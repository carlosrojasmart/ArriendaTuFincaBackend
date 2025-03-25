package com.arriendatufinca.arriendatufinca.DTO;

import lombok.Data;

@Data
public class PropertySearchCriteriaDTO {
    private String title;
    private String description;
    private Integer minBathrooms;
    private Integer maxBathrooms;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private Double minArea;
    private Double maxArea;
    private String city;
    private String address;
    private Double minPrice;
    private Double maxPrice;
}
