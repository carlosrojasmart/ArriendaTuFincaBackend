package com.arriendatufinca.arriendatufinca.Repositories;

import com.arriendatufinca.arriendatufinca.Entities.Property;
import com.arriendatufinca.arriendatufinca.Entities.RentalRequest;
import com.arriendatufinca.arriendatufinca.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public interface RentalRequestRepository extends JpaRepository<RentalRequest, Long>, JpaSpecificationExecutor<RentalRequest> {
    
    default List<RentalRequest> findAllForTenant(User tenant) {
        return findAll(Specification.where(belongsToTenant(tenant)));
    }

    static Specification<RentalRequest> belongsToTenant(User tenant) {
        return (root, query, cb) -> 
            cb.equal(root.get("tenant").get("id"), tenant.getId());
    }
    
    boolean existsByTenantAndProperty(User tenant, Property property);
}
