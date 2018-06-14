package com.sg.superherosightings.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Data
@Entity
public class Super {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int superId;
    
    @NotEmpty (message = "Name is required.")
    @NotBlank (message = "Name cannot be blank.")
    @Size(max = 50, message = "Name is too long (50 characters max).")
    private String name;
    
    @Size(max = 50, message = "Name is too long (50 characters max).")
    private String firstName;
    
    @Size(max = 50, message = "Name is too long (50 characters max).")
    private String lastName;
    
    @Max (value = 2, message = "That alignment is not an option.")
    private int alignment;
    
    @Size(max = 150, message = "Description is too long (150 characters max).")
    private String description;
    
    @Size(max = 500, message = "URL is too long (500 characters max).")
    private String image;
    
    @ManyToMany(mappedBy="supers")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Organization> organizations;

    @ManyToMany(mappedBy="supers")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sighting> sightings;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "AbilitySuper",
        joinColumns = @JoinColumn(name = "SuperId"),
        inverseJoinColumns = @JoinColumn(name = "AbilityId")
    )
    private List<Ability> abilities;
    
    public String getAlignmentDescription(){
        switch (this.alignment) {
            case 1:
                return "Hero";
            case 2:
                return "Villain";
            default:
                return "Undetermined";
        }
    }
    
    public boolean containsAbility(Ability ability) {
        return abilities.stream().anyMatch(a -> a.getAbilityId() == ability.getAbilityId());
    }
    
}
