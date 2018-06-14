/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author amanda
 */
@Data
@Entity
public class Sighting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sightingId;
    
    @Size(max = 500, message = "Description is too long (500 characters max).")
    private String description;
    
    @NotNull
    @PastOrPresent(message = "Date must be today or in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "SightingSuper",
        joinColumns = @JoinColumn(name = "SightingId"),
        inverseJoinColumns = @JoinColumn(name = "SuperId")
    )
    private List<Super> supers;
    
    public boolean containsSuper(Super superhero) {
        return supers.stream().anyMatch(s -> s.getSuperId() == superhero.getSuperId());
    }
    
    public boolean hasLocation(Location loc) {
        return location.getLocationId() == loc.getLocationId();
    }
    
}
