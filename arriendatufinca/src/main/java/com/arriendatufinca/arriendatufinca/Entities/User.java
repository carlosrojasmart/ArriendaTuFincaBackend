package com.arriendatufinca.arriendatufinca.Entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE user SET status = 1 WHERE id=?")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private StatusEnum status = StatusEnum.ACTIVE;

    // As landlord (owned properties)
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<Property> ownedProperties = new ArrayList<>();

    // As tenant (rented properties)
    @ManyToMany
    @JoinTable(
        name = "user_rented_properties",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property> rentedProperties = new ArrayList<>();

    // Rental requests created by the tenant
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<RentalRequest> rentalRequests = new ArrayList<>();
}