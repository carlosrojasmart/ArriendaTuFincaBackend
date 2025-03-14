package com.arriendatufinca.arriendatufinca.Services.admin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

     public Property updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        // Buscar la propiedad en la base de datos
        Property existingProperty = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Verificar y actualizar solo los campos diferentes
        if (!Objects.equals(existingProperty.getTitle(), propertyDTO.getTitle())) {
            existingProperty.setTitle(propertyDTO.getTitle());
        }
        if (!Objects.equals(existingProperty.getDescription(), propertyDTO.getDescription())) {
            existingProperty.setDescription(propertyDTO.getDescription());
        }
        if (existingProperty.getBathrooms() != propertyDTO.getBathrooms()) {
            existingProperty.setBathrooms(propertyDTO.getBathrooms());
        }
        if (existingProperty.getBedrooms() != propertyDTO.getBedrooms()) {
            existingProperty.setBedrooms(propertyDTO.getBedrooms());
        }
        if (existingProperty.getArea() != propertyDTO.getArea()) {
            existingProperty.setArea(propertyDTO.getArea());
        }
        if (!Objects.equals(existingProperty.getCity(), propertyDTO.getCity())) {
            existingProperty.setCity(propertyDTO.getCity());
        }
        if (!Objects.equals(existingProperty.getAddress(), propertyDTO.getAddress())) {
            existingProperty.setAddress(propertyDTO.getAddress());
        }
        if (existingProperty.getPrice() != propertyDTO.getPrice()) {
            existingProperty.setPrice(propertyDTO.getPrice());
        }

        // Guardar los cambios en la base de datos
        return propertyRepository.save(existingProperty);
    } 

    public List<String> getPropertyNamesByUserId(Long landlordId) {
        // Buscar el usuario en la base de datos  
        User landlord = userRepository.findById(landlordId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener todas las propiedades del usuario y extraer solo los nombres
        return propertyRepository.findByLandlord(landlord)
                .stream()
                .map(Property::getTitle) // Extrae solo el título
                .collect(Collectors.toList());
    }
    
}
