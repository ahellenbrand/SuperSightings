package com.sg.superherosightings.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Data
@Entity
public class Organization {
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organizationId;
    
    @NotEmpty (message = "Name is required.")
    @NotBlank (message = "Name cannot be blank.")
    @Size(max = 50, message = "Name is too long (50 characters max).")    
    private String name;
    
    @Size(max = 500, message = "Description is too long (500 characters max).")
    private String description;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "LocationId")
    private Location location;
    
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "OrganizationSuper",
        joinColumns = @JoinColumn(name = "OrganizationId"),
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
