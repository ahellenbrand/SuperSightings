package com.sg.superherosightings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sg.superherosightings.data.OrganizationRepository;
import com.sg.superherosightings.data.SuperRepository;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Result;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class OrganizationService {
    
    @Autowired
    private OrganizationRepository orgRepo;
    
    @Autowired
    private SuperRepository superRepo;
    
    public List<Organization> findAll() {
        return orgRepo.findAll();
    }
    
    public Result<Organization> findById(int organizationId) {
        Result<Organization> result = new Result<>();
        result.setPayload(orgRepo.findById(organizationId).orElse(null));
        return result;
    }
    
    public Result deleteById(int organizationId) {
        orgRepo.deleteById(organizationId);
        return new Result<>();
    }
    
    public Result<Organization> save(Organization organization) {
        Result<Organization> result = validate(organization);
        if (result.isSuccess()) {
           organization = orgRepo.save(organization);
           result.setPayload(organization);
        }
        return result;
    }
    
    private Result<Organization> validate(Organization organization) {
        Result<Organization> result = new Result<>();
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Organization>> errs = validator.validate(organization);
        for (ConstraintViolation<Organization> err : errs) {
            result.addMessage(err.getMessage());
        }
        
        if (result.isSuccess()) {
            validateName(organization, result);
        }
        
        return result;
    }
    
    private void validateName(Organization organization, Result<Organization> result) {
        Organization existing = orgRepo.findByName(organization.getName());
        if(existing != null && organization.getOrganizationId() != existing.getOrganizationId()){
            result.addMessage("An organization with that name already exists.");
        }
    }
    
}
