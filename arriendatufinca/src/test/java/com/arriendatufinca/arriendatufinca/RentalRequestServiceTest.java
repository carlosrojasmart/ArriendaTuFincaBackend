package com.arriendatufinca.arriendatufinca;

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
import static org.mockito.Mockito.*;

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
}