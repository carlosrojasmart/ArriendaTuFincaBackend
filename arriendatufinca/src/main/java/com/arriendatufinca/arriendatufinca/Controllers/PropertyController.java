package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;
import com.arriendatufinca.arriendatufinca.Services.admin.PropertyAdminService;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertySearchService searchService; 
    private final PropertyAdminService adminService;

    public PropertyController(PropertySearchService searchService, PropertyAdminService adminService) {
        this.searchService = searchService;
        this.adminService = adminService;
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
    @PostMapping("/create")
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) {
        Property savedProperty = adminService.createProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }
}
