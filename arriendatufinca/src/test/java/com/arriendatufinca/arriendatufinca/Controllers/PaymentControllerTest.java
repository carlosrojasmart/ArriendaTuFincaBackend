package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.PaymentDTO;
import com.arriendatufinca.arriendatufinca.Enums.PaymentState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) 
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService; 

    @InjectMocks
    private PaymentController paymentController;

    @Test
    public void testProcessPayment_Success() {
        // 1. Configurar datos de prueba
        PaymentDTO inputDTO = new PaymentDTO();
        inputDTO.setRentalRequestId(1L);
        inputDTO.setAmount(1500.00);

        PaymentDTO outputDTO = new PaymentDTO();
        outputDTO.setRentalRequestId(1L);
        outputDTO.setAmount(1500.00);
        outputDTO.setState(PaymentState.COMPLETED);
        outputDTO.setTransactionId(123456L);

        // 2. Simular el servicio
        when(paymentService.processPayment(any(PaymentDTO.class)))
            .thenReturn(outputDTO);

        // 3. Llamar al método del controlador
        ResponseEntity<PaymentDTO> response = paymentController.processPayment(inputDTO);

        // 4. Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputDTO, response.getBody());
    }

    @Test
    public void testProcessPayment_ServiceThrowsException() {
        // 1. Configurar DTO de entrada
        PaymentDTO inputDTO = new PaymentDTO();
        inputDTO.setRentalRequestId(1L);

        // 2. Simular que el servicio lanza una excepción
        when(paymentService.processPayment(any(PaymentDTO.class)))
            .thenThrow(new RuntimeException("Error en el servicio"));

        // 3. Llamar al método y verificar que propaga la excepción
        try {
            paymentController.processPayment(inputDTO);
        } catch (RuntimeException e) {
            assertEquals("Error en el servicio", e.getMessage());
        }
    }
}
