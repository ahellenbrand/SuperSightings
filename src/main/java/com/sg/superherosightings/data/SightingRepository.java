package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Sighting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SightingRepository extends JpaRepository<Sighting, Integer> {
    
    @Query
    (value = "SELECT * FROM Sighting ORDER BY Date DESC, Time DESC LIMIT 10", nativeQuery = true)
    List<Sighting> findRecent();
        
}
