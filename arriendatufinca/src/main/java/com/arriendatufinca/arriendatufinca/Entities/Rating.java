package com.arriendatufinca.arriendatufinca.Entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
@Filter(name = "statusFilter", condition = "status = 0") // Filtra solo los registros activos
@SQLDelete(sql = "UPDATE rating SET status = 1 WHERE id=?") // Borrado lógico
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RentalRequest request; // Relación con la solicitud de arriendo

    @Min(1)
    @Max(5)
    private int score; // Puntuación de la calificación (del 1 al 5)

    @Size(max = 500)
    private String comment; // Comentario de la calificación (máximo 500 caracteres)

    private LocalDateTime date; // Fecha de la calificación

    private StatusEnum status = StatusEnum.ACTIVE; // Estado de la calificación (ACTIVE o DELETED)

    @Enumerated(EnumType.STRING) // Mapea el enum como una cadena en la base de datos
    private RatingType type; // Tipo de calificación (FOR_LANDLORD o FOR_TENANT o FOR_PROPERTY)

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now(); // Establece la fecha automáticamente al crear la calificación
    }
}