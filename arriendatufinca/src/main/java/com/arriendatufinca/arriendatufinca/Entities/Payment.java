package com.arriendatufinca.arriendatufinca.Entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
