package com.arriendatufinca.arriendatufinca.Entities;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE photo SET status = 1 WHERE id=?")
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
    private PaymentState state = PaymentState.PENDING; 
    private StatusEnum status = StatusEnum.ACTIVE;
}
