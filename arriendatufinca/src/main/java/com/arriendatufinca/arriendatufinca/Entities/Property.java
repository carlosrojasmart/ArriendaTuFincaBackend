package com.arriendatufinca.arriendatufinca.Entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import com.arriendatufinca.arriendatufinca.Enums.PropertyStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE property SET status = 'INACTIVE' WHERE id=?")
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord; // Owner of the property

    private String title;
    private String description;
    private int bathrooms;
    private int bedrooms;
    private double area;
    private String city;
    private String address;
    private double price;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status; // Enum: ACTIVE, INACTIVE

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalRequest> rentalRequests = new ArrayList<>();

    @ManyToMany(mappedBy = "rentedProperties")
    private List<User> tenants = new ArrayList<>();
}