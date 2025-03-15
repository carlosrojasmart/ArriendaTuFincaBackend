package com.arriendatufinca.arriendatufinca.Entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

import jakarta.persistence.*;
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
@SQLDelete(sql = "UPDATE rental_requests SET status = 1 WHERE id = ?")
@Table(name = "rental_requests")
public class RentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Enumerated(EnumType.STRING)
    private RequestState state = RequestState.PENDING;
    
    private StatusEnum status = StatusEnum.ACTIVE;

    private LocalDateTime createdAt = LocalDateTime.now();
}
