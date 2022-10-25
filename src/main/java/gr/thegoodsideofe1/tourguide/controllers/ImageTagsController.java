package gr.thegoodsideofe1.tourguide.controllers;


import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.services.ImageTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/image_tags")
public class ImageTagsController {
    @Autowired
    ImageTagsService imageTagsService;

    @GetMapping("")
    public List<ImageTags> list(){
        return imageTagsService.listAllImageTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageTags> get (@PathVariable Integer id){
        try{
            ImageTags imageTags = imageTagsService.getImageTags(id);
            return new ResponseEntity<ImageTags>(imageTags, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<ImageTags>(HttpStatus.NOT_FOUND);
        }
    }
}
