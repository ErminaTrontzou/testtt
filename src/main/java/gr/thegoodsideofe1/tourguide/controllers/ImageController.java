package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/images")
@ResponseBody
public class ImageController {
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return imageService.listAllImages();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }


    @RequestMapping(value = "/getByTitle/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> imageByTitle(@PathVariable String title,
                                        @RequestParam(value="page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "8") int size){
        return imageService.getImageByTitle(title,page,size);
    }
}

