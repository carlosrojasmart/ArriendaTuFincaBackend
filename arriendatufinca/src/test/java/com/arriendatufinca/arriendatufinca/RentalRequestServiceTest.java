package com.arriendatufinca.arriendatufinca;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalRequestServiceTest {
    @Mock
    private RentalRequestRepository rentalRequestRepository;
    
    @InjectMocks
    private RentalRequestService rentalRequestService;

    @Test
    void getRequestsForTenant_ValidTenant_ReturnsRequests() {
        // Arrange
        User tenant = new User();
        tenant.setId(1L);
        
        RentalRequest request = new RentalRequest();
        request.setId(1L);
        request.setTenant(tenant);
        
        when(rentalRequestRepository.findAllForTenant(tenant))
            .thenReturn(List.of(request));

        // Act
        List<RentalRequest> result = rentalRequestService.getRequestsForCurrentTenant(tenant);

        // Assert
        assertEquals(1, result.size());
        assertEquals(tenant.getId(), result.get(0).getTenant().getId());
    }

    @Test
    void getRequestsForLandlord_ValidLandlord_ReturnsRequests() {
        User landlord = new User();
        landlord.setId(1L);

        Property property = new Property();
        property.setId(1L);
        property.setLandlord(landlord);

        RentalRequest request = new RentalRequest();
        request.setId(1L);
        request.setProperty(property);

        when(rentalRequestRepository.findByPropertyLandlordId(1L))
                .thenReturn(List.of(request));

        List<RentalRequest> result = rentalRequestService.getRequestsForLandlord(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getProperty().getLandlord().getId());
    }
}