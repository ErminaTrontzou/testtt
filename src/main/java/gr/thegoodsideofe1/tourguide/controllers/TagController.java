package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.services.TagService;
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
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("")
    public List<Tag> list(){
        return tagService.listAllTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> get (@PathVariable Integer id){
        try{
            Tag tag = tagService.getTag(id);
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
        }
    }
}
