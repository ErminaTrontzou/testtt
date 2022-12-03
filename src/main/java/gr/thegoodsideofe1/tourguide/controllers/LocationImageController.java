package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.LocationImage;
import gr.thegoodsideofe1.tourguide.services.LocationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location_images")
public class LocationImageController {

    @Autowired
    LocationImageService locationImageService;

    @GetMapping("")
    public List<LocationImage> list(){
        return locationImageService.listAllLocationImages();
    }
}
