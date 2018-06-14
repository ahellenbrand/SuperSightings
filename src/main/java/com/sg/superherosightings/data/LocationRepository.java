package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    
    @Query(value = "SELECT * FROM Location l\n" +
        " LEFT OUTER JOIN Organization o ON o.LocationId = l.LocationId\n" +
        " LEFT OUTER JOIN Sighting s ON s.LocationId = l.LocationId\n" +
        " WHERE o.OrganizationId IS NULL AND s.SightingId IS NULL;", nativeQuery = true)
    List<Location> findUnattachedLocations();
    
    List<Location> findByLatitude(double latitude);
    
}
