package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.PropertyState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;
import com.arriendatufinca.arriendatufinca.Services.admin.PropertyAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

class PropertyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PropertySearchService searchService;

    @Mock
    private PropertyAdminService adminService;

    @InjectMocks
    private PropertyController propertyController;

    private Property property;
    private PropertyDTO propertyDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();

        User landlord = new User();
        landlord.setId(1L);  // ID del usuario propietario
        propertyDTO = new PropertyDTO(1L,"Casa grande", "Casa en el centro", 2, 3, 120.0, "Bogotá", "Calle 123", 1000000.0);
        property = new Property(1L, landlord, "Casa grande","Casa en el centro",2,3,120.0,"Bogotá","Calle 123",1000000.0,
                StatusEnum.ACTIVE,PropertyState.ACTIVE,new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @SuppressWarnings("null")
    @Test
    public void searchProperties_ShouldReturnFilteredProperties() {

        PropertySearchCriteriaDTO criteria = new PropertySearchCriteriaDTO();
        criteria.setCity("Bogotá");

        PropertyDTO property1 = new PropertyDTO();
        property1.setTitle("Casa en Bogotá");
        property1.setCity("Bogotá");
        
        PropertyDTO property2 = new PropertyDTO();
        property2.setTitle("Apartamento en Bogotá");
        property2.setCity("Bogotá");

        List<PropertyDTO> mockResults = Arrays.asList(property1, property2);

        when(propertySearchService.searchProperties(any(PropertySearchCriteriaDTO.class)))
            .thenReturn(mockResults);

        // Act
        ResponseEntity<List<PropertyDTO>> response = propertyController.searchProperties(criteria);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Bogotá", response.getBody().get(0).getCity());
    }

    @Test
    void testCreateProperty() throws Exception {
        when(adminService.createProperty(any(PropertyDTO.class))).thenReturn(property);

        mockMvc.perform(post("/api/properties/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(propertyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Casa grande"));

        verify(adminService).createProperty(any(PropertyDTO.class));
    }

    @Test
    void testUpdateProperty() throws Exception {
        when(adminService.updateProperty(eq(1L), any(PropertyDTO.class))).thenReturn(property);

        mockMvc.perform(put("/api/properties/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(propertyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Casa grande"));

        verify(adminService).updateProperty(eq(1L), any(PropertyDTO.class));
    }

    @Test
    void testGetPropertiesByLandlord() throws Exception {
        when(adminService.getPropertyNamesByUserId(1L)).thenReturn(List.of("Casa grande"));

        mockMvc.perform(get("/api/properties/landlord/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0]").value("Casa grande"));

        verify(adminService).getPropertyNamesByUserId(1L);
    }

    @Test
    void testDeactivateProperty() throws Exception {
        when(adminService.deactivateProperty(1L)).thenReturn(property);

        mockMvc.perform(put("/api/properties/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Casa grande"));

        verify(adminService).deactivateProperty(1L);
    }
}
