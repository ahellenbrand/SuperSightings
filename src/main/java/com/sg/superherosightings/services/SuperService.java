package com.sg.superherosightings.services;

import com.sg.superherosightings.data.AbilityRepository;
import com.sg.superherosightings.data.SuperRepository;
import com.sg.superherosightings.models.Ability;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperService {

    @Autowired
    private SuperRepository superRepo;

    @Autowired
    private AbilityRepository abilityRepo;

    public List<Super> findAll() {
        return superRepo.findAll();
    }

    public List<Ability> getAllAbilities() {
        return abilityRepo.findAll();
    }

    public List<Super> supersToList(int[] superIds) {
        List<Super> supers = new ArrayList<>();
        for (int i : superIds) {
            supers.add(superRepo.findById(i).orElse(null));
        }
        return supers;
    }

    public List<Ability> abilitiesToList(int[] abilityIds) {
        List<Ability> abilities = new ArrayList<>();
        for (int i : abilityIds) {
            abilities.add(abilityRepo.findById(i).orElse(null));
        }
        return abilities;
    }

    public Result<Super> findById(int superId) {
        Result<Super> result = new Result<>();
        result.setPayload(superRepo.findById(superId).orElse(null));
        return result;
    }

    //Transactional to make sure first database access save method is completely finished before the second db access delete method.
    @Transactional
    public Result deleteById(int superId) {
        Super sprPerson = superRepo.findById(superId).orElse(null);
        if (sprPerson == null) {
            return new Result<>().addMessage("Delete failed.");
        }
        
        //These relationships could also have been resolved with a query in the SuperRepository
        //to delete these rows from the join tables.
        for (Organization o : sprPerson.getOrganizations()) {
            for (Super s : o.getSupers()) {
                if (s.getSuperId() == sprPerson.getSuperId()) {
                    o.getSupers().remove(s);
                    break;
                }
            }
        }

        for (Sighting sighting : sprPerson.getSightings()) {
            for (Super s : sighting.getSupers()) {
                if (s.getSuperId() == sprPerson.getSuperId()) {
                    sighting.getSupers().remove(s);
                    break;
                }
            }
        }

        superRepo.save(sprPerson);
        superRepo.deleteById(superId);
        return new Result<>();
    }

    public Result<Super> save(Super sprPerson) {
        Result<Super> result = validate(sprPerson);
        if (result.isSuccess()) {
           sprPerson = superRepo.save(sprPerson);
           result.setPayload(sprPerson);
        }
        return result;
    }
    
    private Result<Super> validate(Super sprPerson) {
        Result<Super> result = new Result<>();
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Super>> errs = validator.validate(sprPerson);
        for (ConstraintViolation<Super> err : errs) {
            result.addMessage(err.getMessage());
        }
        
        if (result.isSuccess()) {
            validateName(sprPerson, result);
        }
        
        return result;
    }

    private void validateName(Super sprPerson, Result<Super> result) {
        Super existing = superRepo.findByName(sprPerson.getName());
        if (existing != null && sprPerson.getSuperId() != existing.getSuperId()) {
            result.addMessage(String.format("Super name %s is a duplicate name.", sprPerson.getName()));
        }
    }

}
