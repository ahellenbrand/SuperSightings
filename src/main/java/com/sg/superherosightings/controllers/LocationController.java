package com.sg.superherosightings.controllers;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Result;
import com.sg.superherosightings.services.LocationService;
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
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/locations")
    public String viewAll(Model model) {
        List<Location> locations = locationService.setUnattachedLocations();
        model.addAttribute("locations", locations);
        return "/location/locations";
    }
    
    @GetMapping("/location/view/{locationId}")
    public String view(@PathVariable int locationId, Model model) {
        List<Location> locations = locationService.setUnattachedLocations();
        Result<Location> result = locationService.findById(locationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("location", result.getPayload());
        return "/location/location-view";
    }

    @GetMapping("/location/edit/{locationId}")
    public String edit(@PathVariable int locationId, Model model) {
        Result<Location> result = locationService.findById(locationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("location", result.getPayload());
        return "/location/location-edit";
    }

    @PostMapping("/location/edit/{locationId}")
    public String edit(@Valid Location location, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("location", location);
            return "/location/location-edit";
        }
        
        Result<Location> savedResult = save(location, result, model);        
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("location", location);
            return "/location/location-edit";
        }
        
        int savedLocationId = savedResult.getPayload().getLocationId();
        
        return "redirect:/location/view/" + savedLocationId;
    }
    
    @GetMapping("/location/add")
    public String add(Model model) {
        model.addAttribute("location", new Location());
        return "/location/location-add";
    }
    
    @PostMapping("/location/add")
    public String add(@Valid Location location, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("location", location);
            return "/location/location-add";
        }
        
        Result<Location> savedResult = save(location, result, model);        
        
        if (!savedResult.isSuccess()) {
            model.addAttribute("location", location);
            return "/location/location-add";
        }
        
        int savedLocationId = savedResult.getPayload().getLocationId();
        
        return "redirect:/location/view/" + savedLocationId;
    }
    
    //Probably better to use a post method for delete
    @GetMapping("/location/delete/{locationId}")
    public String delete(@PathVariable int locationId, Model model) {
        Result<Location> result = locationService.findById(locationId);
        if(!result.isSuccess() || result.getPayload() == null){
            return "redirect:/";
        }
        model.addAttribute("location", result.getPayload());
        locationService.deleteById(locationId);
        return "redirect:/locations";
        
    }
    
    private Result<Location> save(Location location, BindingResult result, Model model) {
        Result<Location> saveResult = locationService.save(location);
        if(!saveResult.isSuccess()) {
            if (saveResult.getMessages().stream().anyMatch(m -> m.contains("coordinates"))) {
                result.rejectValue("latitude", "error.location", "Cannot duplicate coordinates.");
                result.rejectValue("longitude", "error.location", "Cannot duplicate coordinates.");
            }
        }
        return saveResult;
    }
}
