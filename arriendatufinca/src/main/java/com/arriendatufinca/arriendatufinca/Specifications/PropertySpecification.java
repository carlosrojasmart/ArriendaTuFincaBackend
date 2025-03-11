package com.arriendatufinca.arriendatufinca.Specifications;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecification {

    public static Specification<Property> withTitle(String title) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Property> withDescription(String description) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Property> withMinBathrooms(Integer minBathrooms) {
        return (root, query, cb) -> 
            cb.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms);
    }

    public static Specification<Property> withMaxBathrooms(Integer maxBathrooms) {
        return (root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("bathrooms"), maxBathrooms);
    }

    public static Specification<Property> withMinBedrooms(Integer minBedrooms) {
        return (root, query, cb) -> 
            cb.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms);
    }

    public static Specification<Property> withMaxBedrooms(Integer maxBedrooms) {
        return (root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("bedrooms"), maxBedrooms);
    }

    public static Specification<Property> withMinArea(Double minArea) {
        return (root, query, cb) -> 
            cb.greaterThanOrEqualTo(root.get("area"), minArea);
    }

    public static Specification<Property> withMaxArea(Double maxArea) {
        return (root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("area"), maxArea);
    }

    public static Specification<Property> withCity(String city) {
        return (root, query, cb) -> 
            cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }

    public static Specification<Property> withAddress(String address) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    public static Specification<Property> withMinPrice(Double minPrice) {
        return (root, query, cb) -> 
            cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Property> withMaxPrice(Double maxPrice) {
        return (root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}