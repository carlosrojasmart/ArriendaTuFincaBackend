package com.arriendatufinca.arriendatufinca.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestDTO;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tenant/rental-requests")
@RequiredArgsConstructor
public class RentalRequestSearchController {
    private final RentalRequestService rentalRequestService; 
    private final ModelMapper modelMapper; 

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<RentalRequestResponse>> getTenantRequests(@PathVariable Long tenantId) {
        // Obtener solicitudes de arriendo para el inquilino
        List<RentalRequestDTO> requests = rentalRequestService.getRequestsForCurrentTenant(tenantId);

        // Mapear a DTO de respuesta y devolver
        return ResponseEntity.ok(
            requests.stream()
                    .map(this::toResponse)
                    .toList()
        );
    }

    private RentalRequestResponse toResponse(RentalRequestDTO request) {
        return modelMapper.map(request, RentalRequestResponse.class);
    }

    // DTO de respuesta
    public record RentalRequestResponse(
        Long id,
        RequestState state,
        LocalDateTime createdAt,
        String propertyTitle
    ) {}
}