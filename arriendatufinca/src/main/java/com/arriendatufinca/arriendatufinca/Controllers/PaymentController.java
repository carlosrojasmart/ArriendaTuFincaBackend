package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.Entities.Payment;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay-rent")
    public ResponseEntity<Payment> payRent(
        @RequestParam Long rentalRequestId,
        @RequestParam double amount,
        @RequestParam String transactionId
    ) {
        Payment payment = paymentService.processPayment(rentalRequestId, amount, transactionId);
        return ResponseEntity.ok(payment);
    }
}