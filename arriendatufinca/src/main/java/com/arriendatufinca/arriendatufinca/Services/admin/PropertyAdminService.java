package com.arriendatufinca.arriendatufinca.Services.admin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.PropertyState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;
@Service
public class PropertyAdminService {

    @Autowired
    private PropertyRepository propertyRepository; 
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;
 
    public PropertyDTO createProperty(PropertyDTO propertyDTO) { 
        try {
            System.out.println("Recibiendo DTO: " + propertyDTO);
    
            // Buscar el propietario en la base de datos
            User landlord = userRepository.findById(propertyDTO.getLandlordId())  
                    .orElseThrow(() -> new RuntimeException("Landlord not found"));
            System.out.println("Propietario encontrado: " + landlord);
    
            // Mapear DTO a entidad
            Property property = modelMapper.map(propertyDTO, Property.class); 
            System.out.println("Propiedad mapeada: " + property);
    
            property.setLandlord(landlord);
            property.setStatus(StatusEnum.ACTIVE); // Estado por defecto 
            property.setState(PropertyState.ACTIVE); // Estado por defecto 
    
            // Guardar en la base de datos
            System.out.println("Guardando propiedad...");
            Property savedProperty = propertyRepository.save(property);
            System.out.println("Propiedad guardada con ID: " + savedProperty.getId());
    
            // Mapear de vuelta a DTO y devolver
            PropertyDTO savedPropertyDTO = modelMapper.map(savedProperty, PropertyDTO.class);
            savedPropertyDTO.setId(savedProperty.getId());
            return savedPropertyDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error guardando propiedad: " + e.getMessage());
        }
    }

    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        Property existingProperty = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    
        modelMapper.map(propertyDTO, existingProperty); 
    
        Property updatedProperty = propertyRepository.save(existingProperty);
        return modelMapper.map(updatedProperty, PropertyDTO.class);
    }
    
    public List<String> getPropertyNamesByUserId(Long landlordId) {
        if (landlordId == null) {
            throw new IllegalArgumentException("El ID del propietario no puede ser nulo");
        }
    
        User landlord = userRepository.findById(landlordId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        List<Property> properties = Optional.ofNullable(propertyRepository.findByLandlord(landlord))
                .orElse(Collections.emptyList());
    
        return properties.stream()
                .map(Property::getTitle)
                .collect(Collectors.toList());
    }
    
    public PropertyDTO deactivateProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    
        property.setState(PropertyState.INACTIVE);
        Property updatedProperty = propertyRepository.save(property);
    
        return modelMapper.map(updatedProperty, PropertyDTO.class);
    }
}
