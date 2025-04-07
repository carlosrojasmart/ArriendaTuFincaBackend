package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.DTO.PropertySearchCriteriaDTO;
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
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO createdProperty = adminService.createProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updatedProperty = adminService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/landlord/{landlordId}") 
    public ResponseEntity<List<String>> getPropertyNamesByUserId(@PathVariable Long landlordId) {
        List<String> propertyNames = adminService.getPropertyNamesByUserId(landlordId);
        return ResponseEntity.ok(propertyNames);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<PropertyDTO> deactivateProperty(@PathVariable Long id) {
        PropertyDTO deactivatedProperty = adminService.deactivateProperty(id);
        return ResponseEntity.ok(deactivatedProperty);
    }
}
