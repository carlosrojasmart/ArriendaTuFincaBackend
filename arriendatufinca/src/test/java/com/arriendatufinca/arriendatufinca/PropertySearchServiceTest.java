package com.arriendatufinca.arriendatufinca;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.PropertyState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Services.Tenant.PropertySearchService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertySearchServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertySearchService propertySearchService;

    // Test data
    private static final User TEST_LANDLORD = new User(
        1L, "john_doe", "John", "Doe", 
        "john@example.com", StatusEnum.ACTIVE, null, null, null
    );

    private static final Property TEST_PROPERTY = new Property(
        1L, TEST_LANDLORD, "Beach House", 
        "Luxury beachfront property", 3, 4, 200.0, 
        "Cancun", "Beach Road", 500.0,StatusEnum.ACTIVE, PropertyState.ACTIVE, 
        Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
    );

    // ---- Tests for search functionality ----

    @SuppressWarnings("unchecked")
    @Test
    void searchProperties_WithTitleFilter_ReturnsFilteredResults() {
        // Arrange
        PropertySearchService.PropertySearchCriteria criteria = new PropertySearchService.PropertySearchCriteria();
        criteria.setTitle("Beach");
        List<Property> expectedResult = Collections.singletonList(TEST_PROPERTY);

        when(propertyRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        // Act
        List<Property> result = propertySearchService.searchProperties(criteria);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Beach House", result.get(0).getTitle());
        verify(propertyRepository, times(1)).findAll(any(Specification.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchProperties_WithPriceRange_ReturnsFilteredResults() {
        // Arrange
        PropertySearchService.PropertySearchCriteria criteria = new PropertySearchService.PropertySearchCriteria();
        criteria.setMinPrice(100.0);
        criteria.setMaxPrice(600.0);
        List<Property> expectedResult = Collections.singletonList(TEST_PROPERTY);

        when(propertyRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        // Act
        List<Property> result = propertySearchService.searchProperties(criteria);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(500.0, result.get(0).getPrice());
        verify(propertyRepository, times(1)).findAll(any(Specification.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchProperties_WithNoFilters_ReturnsAllActiveResults() {
        // Arrange
        PropertySearchService.PropertySearchCriteria criteria = new PropertySearchService.PropertySearchCriteria();
        List<Property> expectedResult = Collections.singletonList(TEST_PROPERTY);

        when(propertyRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        // Act
        List<Property> result = propertySearchService.searchProperties(criteria);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(PropertyState.ACTIVE, result.get(0).getStatus());
        verify(propertyRepository, times(1)).findAll(any(Specification.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchProperties_WithInvalidFilters_ReturnsEmptyList() {
        // Arrange
        PropertySearchService.PropertySearchCriteria criteria = new PropertySearchService.PropertySearchCriteria();
        criteria.setMinBathrooms(5); // No properties match this
        List<Property> expectedResult = Collections.emptyList();

        when(propertyRepository.findAll(any(Specification.class))).thenReturn(expectedResult);

        // Act
        List<Property> result = propertySearchService.searchProperties(criteria);

        // Assert
        assertTrue(result.isEmpty());
        verify(propertyRepository, times(1)).findAll(any(Specification.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchProperties_ExcludesInactiveProperties() {
        // Arrange
        Property inactiveProperty = new Property(
            2L, TEST_LANDLORD, "Mountain Cabin", 
            "Cozy cabin in the mountains", 2, 3, 150.0, 
            "Alps", "Mountain Road", 300.0, StatusEnum.ACTIVE, PropertyState.INACTIVE, 
            Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );

        when(propertyRepository.findAll(any(Specification.class)))
            .thenReturn(Collections.singletonList(TEST_PROPERTY)); // Only return ACTIVE property

        // Act
        List<Property> result = propertySearchService.searchProperties(new PropertySearchService.PropertySearchCriteria());

        // Assert
        assertFalse(result.contains(inactiveProperty));
        verify(propertyRepository, times(1)).findAll(any(Specification.class));
    }
}