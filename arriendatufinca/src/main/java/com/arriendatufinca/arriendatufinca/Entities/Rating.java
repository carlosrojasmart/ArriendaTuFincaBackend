package com.arriendatufinca.arriendatufinca.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import com.arriendatufinca.arriendatufinca.Enums.RatingType;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE rating SET status = 'INACTIVE' WHERE id=?")
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RentalRequest request;

    private int score;
    private String comment;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private RatingType type;
}