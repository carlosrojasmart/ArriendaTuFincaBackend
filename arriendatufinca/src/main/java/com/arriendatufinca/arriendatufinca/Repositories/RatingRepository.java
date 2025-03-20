package com.arriendatufinca.arriendatufinca.Repositories;

import com.arriendatufinca.arriendatufinca.Entities.Rating;
import com.arriendatufinca.arriendatufinca.Enums.RatingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Método para buscar calificaciones por el ID de la solicitud de arriendo
    List<Rating> findByRequestId(Long requestId);

    // Método para buscar calificaciones por tipo (para arrendador o arrendatario)
    List<Rating> findByType(RatingType type);
}