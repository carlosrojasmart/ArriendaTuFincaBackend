package com.arriendatufinca.arriendatufinca.Configuration;

import com.arriendatufinca.arriendatufinca.DTO.PropertyDTO;
import com.arriendatufinca.arriendatufinca.DTO.RentalRequestDTO;
import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // ----- Mapeo para RentalRequestDTO -> RentalRequest -----
        modelMapper.typeMap(RentalRequestDTO.class, RentalRequest.class)
                .addMappings(mapper -> {
                    // Convertir tenantId (Long) -> User (objeto)
                    mapper.<Long>map(RentalRequestDTO::getTenantId, (dest, v) -> dest.getTenant().setId(v));
                    // Convertir propertyId (Long) -> Property (objeto)
                    mapper.<Long>map(RentalRequestDTO::getPropertyId, (dest, v) -> dest.getProperty().setId(v));
                });

        // ----- Mapeo para PropertyDTO -> Property -----
        modelMapper.typeMap(PropertyDTO.class, Property.class)
                .addMappings(mapper -> {
                    // Convertir landlordId (Long) -> User (objeto)
                    mapper.<Long>map(PropertyDTO::getLandlordId, (dest, v) -> dest.getLandlord().setId(v));

                    // Ignorar campos no presentes en el DTO
                    mapper.skip(Property::setId);
                    mapper.skip(Property::setStatus);
                    mapper.skip(Property::setState);
                    mapper.skip(Property::setPhotos);
                    mapper.skip(Property::setRentalRequests);
                    mapper.skip(Property::setTenants);
                });

        return modelMapper;
    }

}
