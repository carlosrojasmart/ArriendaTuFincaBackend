package com.arriendatufinca.arriendatufinca.Repositories;

import com.arriendatufinca.arriendatufinca.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRentalRequestId(Long rentalRequestId);
}