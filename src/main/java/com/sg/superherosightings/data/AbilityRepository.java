package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Integer> {
        
}
