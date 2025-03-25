package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.PropertyResponseDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Enums.PropertyState;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;
import com.arriendatufinca.arriendatufinca.Services.admin.PropertyAdminService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropertyControllerTest {

    private final PropertyAdminService propertyAdminService = mock(PropertyAdminService.class);
    private final PropertySearchService propertySearchService = mock(PropertySearchService.class);
    private final PropertyController propertyController = new PropertyController(propertySearchService, propertyAdminService);
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void deactivatePropertyTest() {
        try {
            // Configuración inicial
            Long propertyId = 1L;
            Property property = new Property();
            property.setId(propertyId);
            property.setState(PropertyState.INACTIVE);

            when(propertyAdminService.deactivateProperty(propertyId)).thenReturn(property);

            // Llamada al controller
            Property result = propertyController.deactivateProperty(propertyId).getBody();
            PropertyResponseDTO responseDTO = modelMapper.map(result, PropertyResponseDTO.class);

            // Aserciones
            assertEquals(propertyId, responseDTO.getId());
            assertEquals(PropertyState.INACTIVE, responseDTO.getState());
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

}
  