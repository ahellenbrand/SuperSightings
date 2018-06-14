package com.sg.superherosightings.services;

import com.sg.superherosightings.data.SightingRepository;
import org.junit.Test;
import static org.mockito.Mockito.mock;

public class SightingServiceTest {
    
    SightingRepository repo;
    SightingService service;
    
    public SightingServiceTest() {
        repo = mock(SightingRepository.class);
        service = new SightingService(repo);
    }
    

    /**
     * Test of findRecent method, of class SightingService.
     */
    @Test
    public void testFindRecent() {
    }

    /**
     * Test of findAll method, of class SightingService.
     */
    @Test
    public void testFindAll() {
    }

    /**
     * Test of findById method, of class SightingService.
     */
    @Test
    public void testFindById() {
    }

    /**
     * Test of deleteById method, of class SightingService.
     */
    @Test
    public void testDeleteById() {
        
    }

    /**
     * Test of update method, of class SightingService.
     */
    @Test
    public void testUpdate() {
    }

    /**
     * Test of save method, of class SightingService.
     */
    @Test
    public void testSave() {
    }
    
}
