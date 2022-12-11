package gr.thegoodsideofe1.tourguide.controllers;


import gr.thegoodsideofe1.tourguide.services.ImageTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/image_tags")
public class ImageTagsController {
    @Autowired
    ImageTagsService imageTagsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return imageTagsService.listAllImageTags();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificImage(@PathVariable int id) {
        return imageTagsService.getImageTags(id);
    }
}
