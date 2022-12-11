package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return tagService.listAllTags();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificImage(@PathVariable int id) {
        return tagService.getTag(id);
    }
}
