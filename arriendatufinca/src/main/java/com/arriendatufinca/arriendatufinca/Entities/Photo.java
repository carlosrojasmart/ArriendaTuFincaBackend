package com.arriendatufinca.arriendatufinca.Entities;

import org.hibernate.annotations.SQLDelete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE photo SET status = 'INACTIVE' WHERE id=?")
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}