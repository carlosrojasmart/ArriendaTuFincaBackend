package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalRequestService {
    private final RentalRequestRepository rentalRequestRepository;
    
    public RentalRequest createRentalRequest(User tenant, Property property) {
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setTenant(tenant);
        rentalRequest.setProperty(property);
        rentalRequest.setCreatedAt(LocalDateTime.now());
        return rentalRequestRepository.save(rentalRequest);
    }

    public List<RentalRequest> getRequestsForCurrentTenant(User currentTenant) {
        return rentalRequestRepository.findAllForTenant(currentTenant);
    }

    public List<RentalRequest> getRequestsForLandlord(Long landlordId) {
        return rentalRequestRepository.findByPropertyLandlordId(landlordId);
    }

    @Transactional
    public RentalRequest approveRentalRequest(Long rentalRequestId) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(rentalRequestId)
                .orElseThrow(() -> new RuntimeException("Solicitud de arriendo no encontrada"));
        rentalRequest.setState(RequestState.APPROVED);
        return rentalRequestRepository.save(rentalRequest);
    }
  
    @Transactional
public RentalRequest rejectRentalRequest(Long rentalRequestId) {
    RentalRequest rentalRequest = rentalRequestRepository.findById(rentalRequestId)
            .orElseThrow(() -> new RuntimeException("Solicitud de arriendo no encontrada"));
    rentalRequest.setState(RequestState.REJECTED);
    return rentalRequestRepository.save(rentalRequest);
}

}
