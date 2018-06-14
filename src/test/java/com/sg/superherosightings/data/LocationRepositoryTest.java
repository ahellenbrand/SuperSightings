package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationRepositoryTest {
    
    @Autowired
    private LocationRepository repo;
    
    public LocationRepositoryTest() {
    }
    
    @Test
    @Ignore
    public void testFindUnattachedLocations() {
        Location location = new Location();
        location.setLatitude(10.00564325);
        location.setLongitude(-10.00564325);

        repo.save(location);

        List<Location> locations = repo.findUnattachedLocations();
        assertTrue(locations.size() >= 1);
    }
    
}
