package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Result;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperServiceTest {
    
    @Autowired  
    SuperService service;
    
    public SuperServiceTest() {
    }
    
    @Test
    public void testFindAll() {
    }

    /**
     * Test of getAllAbilities method, of class SuperService.
     */
    @Test
    public void testGetAllAbilities() {
    }

    /**
     * Test of supersToList method, of class SuperService.
     */
    @Test
    public void testSupersToList() {
    }

    /**
     * Test of abilitiesToList method, of class SuperService.
     */
    @Test
    public void testAbilitiesToList() {
    }

    /**
     * Test of findById method, of class SuperService.
     */
    @Test
    public void testFindById() {
    }

    /**
     * Test of deleteById method, of class SuperService.
     */
    @Test
    @Ignore
    public void testDeleteById() {
        
        Result result = service.deleteById(1);
        
        assertTrue(result.isSuccess());

    }

    /**
     * Test of update method, of class SuperService.
     */
    @Test
    public void testUpdate() {
    }

    /**
     * Test of save method, of class SuperService.
     */
    @Test
    public void testSave() {
    }
    
}
