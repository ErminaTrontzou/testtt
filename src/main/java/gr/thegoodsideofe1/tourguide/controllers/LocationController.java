package gr.thegoodsideofe1.tourguide.controllers;


import gr.thegoodsideofe1.tourguide.entities.Location;
import gr.thegoodsideofe1.tourguide.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("")
    public List<Location> list(){
        return locationService.listAllLocations();
    }
}
