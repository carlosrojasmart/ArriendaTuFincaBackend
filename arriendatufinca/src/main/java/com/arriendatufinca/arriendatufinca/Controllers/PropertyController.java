package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.List;

import com.arriendatufinca.arriendatufinca.DTO.PropertySearchCriteriaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;
import com.arriendatufinca.arriendatufinca.Services.admin.PropertyAdminService;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertySearchService searchService;
    private final PropertyAdminService adminService;

    public PropertyController(PropertySearchService searchService, PropertyAdminService adminService) {
        this.searchService = searchService;
        this.adminService = adminService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<PropertyDTO>> searchProperties( @ModelAttribute PropertySearchCriteriaDTO criteria) {
    List<PropertyDTO> results = searchService.searchProperties(criteria);
    return ResponseEntity.ok(results);
    }

    @PostMapping("/create")
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) {
        Property savedProperty = adminService.createProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        Property updatedProperty = adminService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/landlord/{id}")
    public ResponseEntity<List<String>> getPropertiesByLandlord(@PathVariable Long id) {
        List<String> propertyNames = adminService.getPropertyNamesByUserId(id);
        return ResponseEntity.ok(propertyNames);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Property> deactivateProperty(@PathVariable Long id) {
        Property property = adminService.deactivateProperty(id);
        return ResponseEntity.ok(property);
    }
}
