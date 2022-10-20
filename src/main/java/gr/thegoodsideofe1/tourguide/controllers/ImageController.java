package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("")
    public List<Image> list(){
        return imageService.listAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> get (@PathVariable Integer id){
        try{
            Image image = imageService.getImage(id);
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
    }
}

