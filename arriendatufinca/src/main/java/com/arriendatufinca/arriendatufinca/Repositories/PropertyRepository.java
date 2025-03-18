package com.arriendatufinca.arriendatufinca.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> { 
    List<Property> findByLandlord(User landlord);

}