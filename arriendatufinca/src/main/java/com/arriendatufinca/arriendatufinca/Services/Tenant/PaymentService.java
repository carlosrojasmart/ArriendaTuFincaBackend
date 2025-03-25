package com.arriendatufinca.arriendatufinca.Services.Tenant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.DTO.PaymentDTO;
import com.arriendatufinca.arriendatufinca.Entities.Payment;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Repositories.PaymentRepository;
import com.arriendatufinca.arriendatufinca.Repositories.RentalRequestRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final ModelMapper modelMapper; // Inyecta ModelMapper

    public PaymentDTO processPayment(PaymentDTO paymentDTO) {
        // 1. Obtener la RentalRequest desde la base de datos
        RentalRequest rentalRequest = rentalRequestRepository.findById(paymentDTO.getRentalRequestId())
            .orElseThrow(() -> new RuntimeException("Rental request not found"));

        // 2. Validar el inquilino (opcional)
        if (rentalRequest.getTenant() == null) {
            throw new RuntimeException("Invalid tenant");
        }

        // 3. Mapear PaymentDTO a la entidad Payment
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setRentalRequest(rentalRequest);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setState(PaymentState.COMPLETED); // Estado por defecto

        // 4. Guardar el pago
        Payment savedPayment = paymentRepository.save(payment);

        // 5. Actualizar el estado de la solicitud (opcional)
        rentalRequest.setState(RequestState.APPROVED);
        rentalRequestRepository.save(rentalRequest);

        // 6. Convertir la entidad guardada a DTO y retornarla
        return modelMapper.map(savedPayment, PaymentDTO.class);
    }
}
