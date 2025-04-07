package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.DTO.PropertySearchCriteriaDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Specifications.PropertySpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertySearchService {

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public List<PropertyDTO> searchProperties(PropertySearchCriteriaDTO criteria) {
        Specification<Property> spec = buildSpecification(criteria);
        List<Property> properties = propertyRepository.findAll(spec);
        
        return properties.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Specification<Property> buildSpecification(PropertySearchCriteriaDTO criteria) {
        Specification<Property> spec = Specification.where(null);
        
        // Condiciones de búsqueda originales adaptadas al DTO
        if (StringUtils.hasText(criteria.getTitle())) {
            spec = spec.and(PropertySpecification.withTitle(criteria.getTitle()));
        }
        if (StringUtils.hasText(criteria.getDescription())) {
            spec = spec.and(PropertySpecification.withDescription(criteria.getDescription()));
        }
        if (criteria.getMinBathrooms() != null) {
            spec = spec.and(PropertySpecification.withMinBathrooms(criteria.getMinBathrooms()));
        }
        if (criteria.getMaxBathrooms() != null) {
            spec = spec.and(PropertySpecification.withMaxBathrooms(criteria.getMaxBathrooms()));
        }
        if (criteria.getMinBedrooms() != null) {
            spec = spec.and(PropertySpecification.withMinBedrooms(criteria.getMinBedrooms()));
        }
        if (criteria.getMaxBedrooms() != null) {
            spec = spec.and(PropertySpecification.withMaxBedrooms(criteria.getMaxBedrooms()));
        }
        if (criteria.getMinArea() != null) {
            spec = spec.and(PropertySpecification.withMinArea(criteria.getMinArea()));
        }
        if (criteria.getMaxArea() != null) {
            spec = spec.and(PropertySpecification.withMaxArea(criteria.getMaxArea()));
        }
        if (StringUtils.hasText(criteria.getCity())) {
            spec = spec.and(PropertySpecification.withCity(criteria.getCity()));
        }
        if (StringUtils.hasText(criteria.getAddress())) {
            spec = spec.and(PropertySpecification.withAddress(criteria.getAddress()));
        }
        if (criteria.getMinPrice() != null) {
            spec = spec.and(PropertySpecification.withMinPrice(criteria.getMinPrice()));
        }
        if (criteria.getMaxPrice() != null) {
            spec = spec.and(PropertySpecification.withMaxPrice(criteria.getMaxPrice()));
        }
        
        return spec;
    }

    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO dto = modelMapper.map(property, PropertyDTO.class);
        dto.setLandlordId(property.getLandlord().getId());
        return dto;
    } 
 
    public class PropertySearchCriteria {
    }
}
