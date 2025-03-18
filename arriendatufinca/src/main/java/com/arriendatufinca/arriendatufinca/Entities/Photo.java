package com.arriendatufinca.arriendatufinca.Entities;

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
@SQLDelete(sql = "UPDATE photo SET status = 0 WHERE id=?")
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    private StatusEnum status = StatusEnum.ACTIVE;
}