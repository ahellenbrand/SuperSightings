/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.models.Sighting;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Validator;
import com.sg.superherosightings.data.SightingRepository;
import com.sg.superherosightings.models.Super;

/**
 *
 * @author amanda
 */
@Service
public class SightingService {
    
    private SightingRepository sightingRepo;
    
    @Autowired
    public SightingService(SightingRepository sightingRepo){
        this.sightingRepo = sightingRepo;
    }
    
    public List<Sighting> findRecent() {
        return sightingRepo.findRecent();
    }
    
    public List<Sighting> findAll() {
        return sightingRepo.findAll();
    }
    
    public Result<Sighting> findById(int sightingId) {
        Result<Sighting> result = new Result<>();
        result.setPayload(sightingRepo.findById(sightingId).orElse(null));
        return result;
    }
    
    public Result deleteById(int sightingId) {
        sightingRepo.deleteById(sightingId);
        return new Result<>();
    }
    
    public Result<Sighting> save(Sighting sighting) {
        Result<Sighting> result = validate(sighting);
        
        if (result.isSuccess()) {
            validateSuper(sighting, result);
        }
        
        if (result.isSuccess()) {
           sighting = sightingRepo.save(sighting);
           result.setPayload(sighting);
        }
        
        return result;
    }
    
    private Result<Sighting> validate(Sighting sighting) {
        Result<Sighting> result = new Result<>();
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Sighting>> errs = validator.validate(sighting);
        for (ConstraintViolation<Sighting> err : errs) {
            result.addMessage(err.getMessage());
        }
        
        return result;
    }
    
    private void validateSuper(Sighting sighting, Result<Sighting> result) {
        List<Super> supers = sighting.getSupers();
        if(supers == null){
            result.addMessage("A new sighting must spot at least one super.");
        }
    }
    
}
