package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.OrganizationService;
import com.sg.superherosightings.services.SuperService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrganizationController {
    
    @Autowired
    private OrganizationService orgService;
    
    @Autowired
    private SuperService superService;
    
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/organizations")
    public String viewAll(Model model) {
        List<Organization> organizations = orgService.findAll();
        model.addAttribute("organizations", organizations);
        return "/organization/organizations";
    }
    
    @GetMapping("/organization/view/{organizationId}")
    public String view(@PathVariable int organizationId, Model model) {
        Result<Organization> result = orgService.findById(organizationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("organization", result.getPayload());
        return "/organization/organization-view";
    }

    @GetMapping("/organization/edit/{organizationId}")
    public String edit(@PathVariable int organizationId, Model model) {
        Result<Organization> result = orgService.findById(organizationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("organization", result.getPayload());
        model.addAttribute("allSupers", superService.findAll());
        model.addAttribute("allLocations", locationService.findAll());
        return "/organization/organization-edit";
    }

    @PostMapping("/organization/edit/{organizationId}")
    public String edit(@Valid Organization organization, BindingResult result, int[] checkedSuperIds, Model model) {
        
        if (checkedSuperIds != null) {
            organization.setSupers(superService.supersToList(checkedSuperIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/organization/organization-edit";
        }
        
        Result<Organization> savedResult = save(organization, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("organization", organization);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/organization/organization-edit";
        }
        
        int savedOrganizationId = savedResult.getPayload().getOrganizationId();
        
        return "redirect:/organization/view/" + organization.getOrganizationId();
    }

    @GetMapping("/organization/add")
    public String add(Model model) {
        model.addAttribute("organization", new Organization());
        model.addAttribute("allSupers", superService.findAll());
        model.addAttribute("allLocations", locationService.findAll());
        return "/organization/organization-add";
    }
    
    @PostMapping("/organization/add")
    public String add(@Valid Organization organization, BindingResult result, int[] checkedSuperIds, Model model) {
        
        if (checkedSuperIds != null) {
            organization.setSupers(superService.supersToList(checkedSuperIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/organization/organization-add";
        }
        
        Result<Organization> savedResult = save(organization, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("organization", organization);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/organization/organization-add";
        }
        
        int savedOrganizationId = savedResult.getPayload().getOrganizationId();
        
        return "redirect:/organization/view/" + savedOrganizationId;
    }

    //Probably better to use a post method for delete
    @GetMapping("/organization/delete/{organizationId}")
    public String delete(@PathVariable int organizationId, Model model) {
        Result<Organization> result = orgService.findById(organizationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("organization", result.getPayload());
        orgService.deleteById(organizationId);
        return "redirect:/organizations";
        
    }
    
    private Result<Organization> save(Organization organization, BindingResult result, Model model) {
        Result<Organization> saveResult = orgService.save(organization);
        if(!saveResult.isSuccess()) {
            if (saveResult.getMessages().stream().anyMatch(m -> m.contains("name"))) {
                result.rejectValue("name", "error.organization", "Cannot duplicate organization name.");
            }
        }
        return saveResult;
    }
}
