package com.arriendatufinca.arriendatufinca;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;
import com.arriendatufinca.arriendatufinca.Services.admin.PropertyAdminService;

@ExtendWith(MockitoExtension.class)
class PropertyAdminServiceTest {

    @Mock
    private PropertyRepository propertyRepository;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PropertyAdminService propertyAdminService;

    private PropertyDTO propertyDTO;
    private Property property;
    private User landlord;

    @BeforeEach
    void setUp() {
        landlord = new User();
        landlord.setId(1L);
        
        propertyDTO = new PropertyDTO();
        propertyDTO.setLandlordId(1L);
        propertyDTO.setTitle("Casa en la playa");
        propertyDTO.setDescription("Hermosa casa frente al mar");
        propertyDTO.setBathrooms(2);
        propertyDTO.setBedrooms(3);
        propertyDTO.setArea(120.5);
        propertyDTO.setCity("Cartagena");
        propertyDTO.setAddress("Calle 123");
        propertyDTO.setPrice(250000.0);
        
        property = new Property();
        property.setId(1L);
        property.setLandlord(landlord);
        property.setTitle("Casa vieja");
        property.setDescription("Casa antigua");
        property.setBathrooms(1);
        property.setBedrooms(2);
        property.setArea(90.0);
        property.setCity("Bogotá");
        property.setAddress("Carrera 45");
        property.setPrice(150000.0);
    }

    @Test
    void testCreateProperty() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(landlord));
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Property savedProperty = propertyAdminService.createProperty(propertyDTO);

        assertNotNull(savedProperty);
        assertEquals("Casa en la playa", savedProperty.getTitle());
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyTitle() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        
        propertyDTO.setTitle("Nueva Casa de Playa");
        Property updatedProperty = propertyAdminService.updateProperty(1L, propertyDTO);
        
        assertEquals("Nueva Casa de Playa", updatedProperty.getTitle());
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void testUpdatePropertyDescription() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        
        propertyDTO.setDescription("Descripción actualizada");
        Property updatedProperty = propertyAdminService.updateProperty(1L, propertyDTO);
        
        assertEquals("Descripción actualizada", updatedProperty.getDescription());
        verify(propertyRepository).save(any(Property.class));
    }

        @Test
    void testGetPropertyNamesByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(landlord));
        when(propertyRepository.findByLandlord(landlord)).thenReturn(Arrays.asList(property));

        List<String> propertyNames = propertyAdminService.getPropertyNamesByUserId(1L);
        
        assertFalse(propertyNames.isEmpty());
        assertEquals("Casa vieja", propertyNames.get(0));
        verify(propertyRepository).findByLandlord(landlord);
    }
}

