package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Sighting;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingRepositoryTest {
    
    @Autowired
    private SightingRepository repo;
    
    //Don't print anything with Lombok (use manual getters and setters) becuase
    //toString() method will cause infinite recursion (will try to print whole
    //object instead of just a field and joined objects are children of each other)
    
    //Assert only 10 sightings are being grabbed.
    @Test
    public void testRecentSightings(){
        List<Sighting> recentSightings = repo.findRecent();
        assertTrue(recentSightings.size() <= 10);
    }

}
