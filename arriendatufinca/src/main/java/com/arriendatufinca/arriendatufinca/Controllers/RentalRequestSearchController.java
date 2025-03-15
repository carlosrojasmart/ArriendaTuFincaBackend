package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tenant/rental-requests")
@RequiredArgsConstructor
public class RentalRequestSearchController {
    private final RentalRequestService rentalRequestService;

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<RentalRequestResponse>> getTenantRequests(@PathVariable Long tenantId) {
        // Create a tenant object with the provided ID
        User tenant = new User();
        tenant.setId(tenantId);
        
        // Fetch rental requests for the tenant
        List<RentalRequest> requests = rentalRequestService.getRequestsForCurrentTenant(tenant);
        
        // Map to DTO and return
        return ResponseEntity.ok(requests.stream()
            .map(this::toResponse)
            .toList());
    }

    private RentalRequestResponse toResponse(RentalRequest request) {
        return new RentalRequestResponse(
            request.getId(),
            request.getState(),
            request.getCreatedAt(),
            request.getProperty().getTitle()
        );
    }

    // DTO
    public record RentalRequestResponse(
        Long id,
        RequestState status,
        LocalDateTime createdAt,
        String propertyTitle
    ) {}
}