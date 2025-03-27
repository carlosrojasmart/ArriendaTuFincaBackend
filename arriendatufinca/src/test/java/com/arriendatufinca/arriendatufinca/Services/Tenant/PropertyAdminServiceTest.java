package com.arriendatufinca.arriendatufinca.Services.Tenant;
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
import org.modelmapper.ModelMapper;

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
    
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PropertyAdminService propertyAdminService;

    private User landlord;
    private Property property;
    private PropertyDTO propertyDTO;

    @BeforeEach
    void setUp() {
        landlord = new User();
        landlord.setId(1L);

        property = new Property();
        property.setId(10L);
        property.setTitle("Finca Bonita");
        property.setLandlord(landlord);

        propertyDTO = new PropertyDTO();
        propertyDTO.setId(10L);
        propertyDTO.setTitle("Finca Bonita");
        propertyDTO.setLandlordId(1L);
    }

    @Test
    void testCreateProperty() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(landlord));
        when(modelMapper.map(propertyDTO, Property.class)).thenReturn(property);
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyAdminService.createProperty(propertyDTO);

        assertNotNull(result);
        assertEquals("Finca Bonita", result.getTitle());
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void testGetPropertyNamesByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(landlord));
        when(propertyRepository.findByLandlord(landlord)).thenReturn(Arrays.asList(property));

        List<String> propertyNames = propertyAdminService.getPropertyNamesByUserId(1L);

        assertNotNull(propertyNames);
        assertFalse(propertyNames.isEmpty());
        assertEquals("Finca Bonita", propertyNames.get(0));
    }

    @Test
    void testUpdateProperty() {
        when(propertyRepository.findById(10L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        when(modelMapper.map(propertyDTO, Property.class)).thenReturn(property);
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyAdminService.updateProperty(10L, propertyDTO);

        assertNotNull(result);
        assertEquals("Finca Bonita", result.getTitle());
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void testDeactivateProperty() {
        when(propertyRepository.findById(10L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyAdminService.deactivateProperty(10L);

        assertNotNull(result);
        verify(propertyRepository).save(any(Property.class));
    }
}


