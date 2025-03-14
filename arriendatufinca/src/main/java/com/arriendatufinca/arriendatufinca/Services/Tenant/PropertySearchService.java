package com.arriendatufinca.arriendatufinca.Services.Tenant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Repositories.PropertyRepository;
import com.arriendatufinca.arriendatufinca.Specifications.PropertySpecification;

import lombok.Data;


@Service
public class PropertySearchService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> searchProperties(PropertySearchCriteria criteria) {
        Specification<Property> spec = Specification.where(null);
        
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
        
        return propertyRepository.findAll(spec);
    } 


    @Data
    public static class PropertySearchCriteria {
        private String title;
        private String description;
        private Integer minBathrooms;
        private Integer maxBathrooms;
        private Integer minBedrooms;
        private Integer maxBedrooms;
        private Double minArea;
        private Double maxArea;
        private String city;
        private String address;
        private Double minPrice;
        private Double maxPrice;
    }
}