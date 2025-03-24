package com.arriendatufinca.arriendatufinca;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arriendatufinca.arriendatufinca.Entities.Payment;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.PaymentRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PaymentService;

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
        rentalRequest.setTenant(new User(1L, "tenant_user", "Tenant", "User", "tenant@example.com", StatusEnum.ACTIVE, null, null, null));
        rentalRequest.setState(RequestState.PENDING);

        when(rentalRequestRepository.findById(1L)).thenReturn(Optional.of(rentalRequest));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Payment payment = paymentService.processPayment(1L, 500.0, "txn_12345");

        // Assert
        assertNotNull(payment);
        assertEquals(PaymentState.COMPLETED, payment.getState());
        assertEquals(500.0, payment.getAmount());
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