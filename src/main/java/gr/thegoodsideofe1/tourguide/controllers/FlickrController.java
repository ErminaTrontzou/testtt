package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/v1/flickr")
@ResponseBody
public class FlickrController {
    @Autowired
    FlickrService flickrService;

    @Transactional
    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<?> getNewImages(@PathVariable String title){
        return flickrService.getNewImagesForLocation(title);
    }

    @PostMapping("/imageToSave")
    public ResponseEntity<?>imageToSave(@RequestBody Image photo){
        return flickrService.saveImage(photo);
    }
}
