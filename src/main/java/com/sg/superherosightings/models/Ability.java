package com.sg.superherosightings.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Data
@Entity
public class Ability {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int abilityId;
    
    @NotEmpty (message = "Name is required.")
    @NotBlank (message = "Name cannot be blank.")
    @Size(max = 50, message = "Name is too long (50 characters max).")    
    private String name;

    @Size(max = 150, message = "Description is too long (150 characters max).")    
    private String description;
    
    @ManyToMany(mappedBy="abilities")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Super> supers;
    
}
