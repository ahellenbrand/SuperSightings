package com.sg.superherosightings.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;
    
    @Size(max = 50, message = "Name is too long (50 characters max).")
    private String name;
    
    //Need to find a way to validate doubles
    @Min(value = -90, message = "Latitude should be no less than -90 degrees.")
    @Max(value = 90, message = "Latitude should be no more than 90 degrees.")
    private double latitude;
    
    //Need to find a way to validate doubles
    @Min(value = -180, message = "Longitude should be no less than 180 degrees.")
    @Max(value = 180, message = "Longitude should be no more than 180 degrees.")
    private double longitude;
    
    @Transient
    private boolean disabled = true;
    
}