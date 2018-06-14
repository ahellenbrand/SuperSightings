package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.services.LocationService;
import com.sg.superherosightings.services.SightingService;
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
public class SightingController {
    
    @Autowired
    private SightingService sightingService;
    
    @Autowired
    private SuperService superService;
    
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/")
    public String index(Model model) {
        List<Sighting> recentSightings = sightingService.findRecent();
        model.addAttribute("sightings", recentSightings);
        return "index";
    }
    
    @GetMapping("/sightings")
    public String viewAll(Model model) {
        model.addAttribute("sightings", sightingService.findAll());
        return "/sighting/sightings";
    }
    
    @GetMapping("/sighting/view/{sightingId}")
    public String view(@PathVariable int sightingId, Model model) {
        Result<Sighting> result = sightingService.findById(sightingId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("sighting", result.getPayload());
        return "/sighting/sighting-view";
    }
    
    @GetMapping("/sighting/edit/{sightingId}")
    public String edit(@PathVariable int sightingId, Model model) {
        Result<Sighting> result = sightingService.findById(sightingId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("sighting", result.getPayload());
        model.addAttribute("allSupers", superService.findAll());
        model.addAttribute("allLocations", locationService.findAll());
        return "/sighting/sighting-edit";
    }
    
    @PostMapping("/sighting/edit/{sightingId}")
    public String edit(@Valid Sighting sighting, BindingResult result, int[] checkedSuperIds, Model model) {
        
        if (checkedSuperIds != null) {
            sighting.setSupers(superService.supersToList(checkedSuperIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/sighting/sighting-edit";
        }
        
        Result<Sighting> savedResult = save(sighting, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/sighting/sighting-edit";
        }
        
        int savedSightingId = savedResult.getPayload().getSightingId();
        
        return "redirect:/sighting/view/" + sighting.getSightingId();
    }
    
    @GetMapping("/sighting/add")
    public String add(Model model) {
        model.addAttribute("sighting", new Sighting());
        model.addAttribute("allSupers", superService.findAll());
        model.addAttribute("allLocations", locationService.findAll());
        return "/sighting/sighting-add";
    }
    
    @PostMapping("/sighting/add")
    public String add(@Valid Sighting sighting, BindingResult result, int[] checkedSuperIds, Model model) {
        
        if (checkedSuperIds != null) {
            sighting.setSupers(superService.supersToList(checkedSuperIds));
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/sighting/sighting-add";
        }
        
        Result<Sighting> savedResult = save(sighting, result, model);
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("allSupers", superService.findAll());
            model.addAttribute("allLocations", locationService.findAll());
            return "/sighting/sighting-add";
        }
        
        int savedSightingId = savedResult.getPayload().getSightingId();
        
        return "redirect:/sighting/view/" + savedSightingId;
    }

    //Probably better to use a post method for delete
    @GetMapping("/sighting/delete/{sightingId}")
    public String delete(@PathVariable int sightingId, Model model) {
        Result<Sighting> result = sightingService.findById(sightingId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("sighting", result.getPayload());
        sightingService.deleteById(sightingId);
        return "redirect:/sightings";
    }
    
    private Result<Sighting> save(Sighting sighting, BindingResult result, Model model) {
        Result<Sighting> saveResult = sightingService.save(sighting);
        if(!saveResult.isSuccess()) {
            if (saveResult.getMessages().stream().anyMatch(m -> m.contains("super"))) {
                result.rejectValue("supers", "error.sighting", "A new sighting must spot at least one super person.");
            }
        }
        return saveResult;
    }
}
