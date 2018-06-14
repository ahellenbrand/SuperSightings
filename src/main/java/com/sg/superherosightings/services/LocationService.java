package com.sg.superherosightings.services;

import com.sg.superherosightings.models.Result;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Validator;
import com.sg.superherosightings.data.LocationRepository;
import com.sg.superherosightings.models.Location;
import java.util.List;

@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepo;
    
    public List<Location> findAll() {
        return locationRepo.findAll();
    }
    
    public Result<Location> findById(int locationId) {
        Result<Location> result = new Result<>();
        result.setPayload(locationRepo.findById(locationId).orElse(null));
        return result;
    }
    
    //Compares all locations to all locations without a foreign key in another table
    //Marks those locations as disabled false making it possible to delete them.
    public List<Location> setUnattachedLocations() {
        List<Location> allLocations = locationRepo.findAll();
        List<Location> unattachedLocations = locationRepo.findUnattachedLocations();
        for(Location location : allLocations){
            if(unattachedLocations.stream().anyMatch(l -> l.getLocationId() == location.getLocationId())){
                location.setDisabled(false);
            }
        }
        return allLocations;
    }
    
    public Result deleteById(int locationId) {
        locationRepo.deleteById(locationId);
        return new Result<>();
    }
    
    public Result<Location> save(Location location) {
        Result<Location> result = validate(location);
        if (result.isSuccess()) {
           location = locationRepo.save(location);
           result.setPayload(location);
        }
        return result;
    }
    
    private Result<Location> validate(Location location) {
        Result<Location> result = new Result<>();
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Location>> errs = validator.validate(location);
        for (ConstraintViolation<Location> err : errs) {
            result.addMessage(err.getMessage());
        }
        
        if (result.isSuccess()) {
            validateCoordinates(location, result);
        }
        
        return result;
    }
    
    private void validateCoordinates(Location location, Result<Location> result) {
        
        List<Location> locations = locationRepo.findByLatitude(location.getLatitude());
        for(Location loc : locations){
            if(location.getLongitude() == loc.getLongitude() && location.getLocationId() != loc.getLocationId()){
                    result.addMessage("A location with those coordinates already exists.");
            }
        }
        
    }
    
}
