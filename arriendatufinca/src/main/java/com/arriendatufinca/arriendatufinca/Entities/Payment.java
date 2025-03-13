package com.arriendatufinca.arriendatufinca.Entities;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import com.arriendatufinca.arriendatufinca.Enums.PaymentStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_request_id", nullable = false)
    private RentalRequest rentalRequest;

    private double amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status = PaymentStatus.PENDING; 
}
