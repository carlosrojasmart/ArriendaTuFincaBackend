package com.arriendatufinca.arriendatufinca.Repositories;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
}