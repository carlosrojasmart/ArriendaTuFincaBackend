package com.arriendatufinca.arriendatufinca.Services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.PropertyStatus;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;
@Service
public class PropertyAdminService {

    @Autowired
    private PropertyRepository propertyRepository; 
    private UserRepository userRepository;
 
    public Property createProperty(PropertyDTO propertyDTO) { 
        // Buscar el propietario en la base de datos
        User landlord = userRepository.findById(propertyDTO.getLandlordId())
                .orElseThrow(() -> new RuntimeException("Landlord not found"));

        // Llenamos el objeto Property
        Property property = new Property(); 
        property.setLandlord(landlord);
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription()); 
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setArea(propertyDTO.getArea());
        property.setCity(propertyDTO.getCity());
        property.setAddress(propertyDTO.getAddress());
        property.setPrice(propertyDTO.getPrice());
        property.setStatus(PropertyStatus.ACTIVE); // Estado por defecto

        return propertyRepository.save(property);
    }

    
}
