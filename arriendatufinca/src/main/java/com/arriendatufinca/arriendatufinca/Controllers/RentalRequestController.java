package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.Entities.*;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rental-requests")
public class RentalRequestController {
    private final RentalRequestService rentalRequestService;

    public RentalRequestController(RentalRequestService rentalRequestService) {
        this.rentalRequestService = rentalRequestService;
    }

    @PostMapping
    public ResponseEntity<RentalRequest> createRentalRequest(
        @RequestParam Long tenantId,
        @RequestParam Long propertyId
    ) {
        User tenant = new User();
        tenant.setId(tenantId);

        Property property = new Property();
        property.setId(propertyId);

        RentalRequest rentalRequest = rentalRequestService.createRentalRequest(tenant, property);
        return ResponseEntity.ok(rentalRequest);
    }

    @GetMapping
    public ResponseEntity<List<RentalRequest>> getRentalRequests(@RequestParam Long tenantId) {
        User tenant = new User();
        tenant.setId(tenantId);

        List<RentalRequest> requests = rentalRequestService.getRequestsForCurrentTenant(tenant);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<RentalRequest>> getRentalRequestsForLandlord(@PathVariable Long landlordId) {
        List<RentalRequest> requests = rentalRequestService.getRequestsForLandlord(landlordId);
        return ResponseEntity.ok(requests);
    }
    @PutMapping("/rental-requests/{id}/approve")
    public ResponseEntity<RentalRequest> approveRentalRequest(@PathVariable Long id) {
        return ResponseEntity.ok(rentalRequestService.approveRentalRequest(id));
    }
    
    @PutMapping("/rental-requests/{id}/reject")
    public ResponseEntity<RentalRequest> rejectRentalRequest(@PathVariable Long id) {
        return ResponseEntity.ok(rentalRequestService.rejectRentalRequest(id));
    }
}