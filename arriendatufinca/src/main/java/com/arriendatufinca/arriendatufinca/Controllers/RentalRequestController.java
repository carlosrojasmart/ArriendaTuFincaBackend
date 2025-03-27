package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestDTO;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;

@RestController
@RequestMapping("/api/rental-requests")
public class RentalRequestController {
    private final RentalRequestService rentalRequestService;

    public RentalRequestController(RentalRequestService rentalRequestService) {
        this.rentalRequestService = rentalRequestService;
    }

    @PostMapping("/create")
    public ResponseEntity<RentalRequestDTO> createRentalRequest(@RequestBody RentalRequestDTO rentalRequestDTO) {
        RentalRequestDTO createdRequest = rentalRequestService.createRentalRequest(rentalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<RentalRequestDTO>> getRequestsForTenant(@PathVariable Long tenantId) {
        List<RentalRequestDTO> requests = rentalRequestService.getRequestsForCurrentTenant(tenantId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<RentalRequestDTO>> getRequestsForLandlord(@PathVariable Long landlordId) {
        List<RentalRequestDTO> requests = rentalRequestService.getRequestsForLandlord(landlordId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{rentalRequestId}/approve")
    public ResponseEntity<RentalRequestDTO> approveRentalRequest(@PathVariable Long rentalRequestId) {
        RentalRequestDTO approvedRequest = rentalRequestService.approveRentalRequest(rentalRequestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PutMapping("/{rentalRequestId}/reject")
    public ResponseEntity<RentalRequestDTO> rejectRentalRequest(@PathVariable Long rentalRequestId) {
        RentalRequestDTO rejectedRequest = rentalRequestService.rejectRentalRequest(rentalRequestId);
        return ResponseEntity.ok(rejectedRequest);
    }
}