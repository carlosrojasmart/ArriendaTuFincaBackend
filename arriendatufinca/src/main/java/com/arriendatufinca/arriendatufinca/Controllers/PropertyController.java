package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertySearchService searchService;

    public PropertyController(PropertySearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Integer minBathrooms,
        @RequestParam(required = false) Integer maxBathrooms,
        @RequestParam(required = false) Integer minBedrooms,
        @RequestParam(required = false) Integer maxBedrooms,
        @RequestParam(required = false) Double minArea,
        @RequestParam(required = false) Double maxArea,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String address,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        PropertySearchService.PropertySearchCriteria criteria = new PropertySearchService.PropertySearchCriteria();
        criteria.setTitle(title);
        criteria.setDescription(description);
        criteria.setMinBathrooms(minBathrooms);
        criteria.setMaxBathrooms(maxBathrooms);
        criteria.setMinBedrooms(minBedrooms);
        criteria.setMaxBedrooms(maxBedrooms);
        criteria.setMinArea(minArea);
        criteria.setMaxArea(maxArea);
        criteria.setCity(city);
        criteria.setAddress(address);
        criteria.setMinPrice(minPrice);
        criteria.setMaxPrice(maxPrice);

        List<Property> results = searchService.searchProperties(criteria);
        return ResponseEntity.ok(results);
    }
}
