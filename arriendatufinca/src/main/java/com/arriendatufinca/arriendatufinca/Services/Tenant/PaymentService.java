package com.arriendatufinca.arriendatufinca.Services.Tenant;

import com.arriendatufinca.arriendatufinca.Entities.*;
import com.arriendatufinca.arriendatufinca.Enums.PaymentStatus;
import com.arriendatufinca.arriendatufinca.Enums.RequestStatus;
import com.arriendatufinca.arriendatufinca.Repositories.PaymentRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalRequestRepository rentalRequestRepository;

    public Payment processPayment(Long rentalRequestId, double amount, String transactionId) {
        // Fetch the rental request
        RentalRequest rentalRequest = rentalRequestRepository.findById(rentalRequestId)
            .orElseThrow(() -> new RuntimeException("Rental request not found"));

        // Validate the tenant (optional, if needed)
        User tenant = rentalRequest.getTenant();
        if (tenant == null) {
            throw new RuntimeException("Invalid tenant");
        }

        // Create a new payment
        Payment payment = new Payment();
        payment.setRentalRequest(rentalRequest);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.COMPLETED);

        // Save the payment
        Payment savedPayment = paymentRepository.save(payment);

        // Update the rental request status (optional)
        rentalRequest.setStatus(RequestStatus.APPROVED);
        rentalRequestRepository.save(rentalRequest);

        return savedPayment;
    }
}