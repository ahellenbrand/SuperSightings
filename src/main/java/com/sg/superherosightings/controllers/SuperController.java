package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.models.Super;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.OrganizationService;
import com.sg.superherosightings.services.SightingService;
import com.sg.superherosightings.services.SuperService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SuperController {
    
    @Autowired
    private SuperService superService;
        
    @Autowired
    private SightingService sightingService;
    
    @Autowired
    private LocationService locationService;
    
    @Autowired
    private OrganizationService orgService;
    
    @GetMapping("/supers")
    public String viewAll(Model model) {
        model.addAttribute("supers", superService.findAll());
        return "/super/supers";
    }
    
    @GetMapping("/super/view/{superId}")
    public String view(@PathVariable int superId, Model model) {
        Result<Super> result = superService.findById(superId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("super", result.getPayload());
        return "/super/super-view";
    }
    
    @GetMapping("/super/edit/{superId}")
    public String edit(@PathVariable int superId, Model model) {
        Result<Super> result = superService.findById(superId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("super", result.getPayload());
        model.addAttribute("allAbilities", superService.getAllAbilities());
        return "/super/super-edit";
    }
    
    @PostMapping("/super/edit/{superId}")
    public String edit(@Valid Super sprPerson, BindingResult result, int[] checkedAbilityIds, Model model) {
        
        if (checkedAbilityIds != null) {
            sprPerson.setAbilities(superService.abilitiesToList(checkedAbilityIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("super", sprPerson);
            model.addAttribute("allAbilities", superService.getAllAbilities());
            return "/super/super-edit";
        }
        
        Result<Super> savedResult = save(sprPerson, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("super", sprPerson);
            model.addAttribute("allAbilities", superService.getAllAbilities());
            return "/super/super-edit";
        }
        
        int savedSuperId = savedResult.getPayload().getSuperId();
        
        return "redirect:/super/view/" + sprPerson.getSuperId();
    }
    
    @GetMapping("/super/add")
        public String add(Model model) {
            model.addAttribute("super", new Super());
            model.addAttribute("allAbilities", superService.getAllAbilities());
        return "/super/super-add";
    }
    
    @PostMapping("/super/add")
    public String add(@Valid Super sprPerson, BindingResult result, int[] checkedAbilityIds, Model model) {
        
        if (checkedAbilityIds != null) {
            sprPerson.setAbilities(superService.abilitiesToList(checkedAbilityIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("super", sprPerson);
            model.addAttribute("allAbilities", superService.getAllAbilities());
            return "/super/super-add";
        }
        
        Result<Super> savedResult = save(sprPerson, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("super", sprPerson);
            model.addAttribute("allAbilities", superService.getAllAbilities());
            return "/super/super-add";
        }
        
        int savedSuperId = savedResult.getPayload().getSuperId();
        
        return "redirect:/super/view/" + savedSuperId;
    }
    
    //Probably better to use a post method for delete
    @GetMapping("/super/delete/{superId}")
    public String delete(@PathVariable int superId, Model model) {
        Result<Super> result = superService.findById(superId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("super", result.getPayload());
        superService.deleteById(superId);
        return "redirect:/supers";
    }
    
    private Result<Super> save(Super sprPerson, BindingResult result, Model model) {
        Result<Super> saveResult = superService.save(sprPerson);
        if(!saveResult.isSuccess()) {
            if (saveResult.getMessages().stream().anyMatch(m -> m.contains ("duplicate name"))) {
                result.rejectValue("name", "error.super", "That super name is already registered.");
            }
        }
        return saveResult;
    }
    
}
