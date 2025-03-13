package com.arriendatufinca.arriendatufinca;

import com.arriendatufinca.arriendatufinca.Entities.*;
import com.arriendatufinca.arriendatufinca.Enums.PaymentStatus;
import com.arriendatufinca.arriendatufinca.Enums.RequestStatus;
import com.arriendatufinca.arriendatufinca.Repositories.PaymentRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PaymentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RentalRequestRepository rentalRequestRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void processPayment_ValidRequest_ReturnsPayment() {
        // Arrange
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setId(1L);
        rentalRequest.setTenant(new User(1L, "tenant_user", "Tenant", "User", "tenant@example.com", "password123", true, null, null, null));
        rentalRequest.setStatus(RequestStatus.PENDING);

        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Payment payment = paymentService.processPayment(1L, 500.0, "txn_12345");

        // Assert
        assertNotNull(payment);
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(500.0, payment.getAmount());
        assertEquals("txn_12345", payment.getTransactionId());
        verify(rentalRequestRepository, times(1)).save(rentalRequest);
    }

    @Test
    void processPayment_InvalidRentalRequest_ThrowsException() {
        // Arrange
        when(rentalRequestRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            paymentService.processPayment(999L, 500.0, "txn_12345")
        );
    }
}