package com.arriendatufinca.arriendatufinca.DTO;

import java.time.LocalDateTime;

import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentDTO {
    private long rentalRequestId; 
    private Long transactionId;   
    private double amount;
    private PaymentState state;
    private LocalDateTime paymentDate; 
    private StatusEnum status;         
}
