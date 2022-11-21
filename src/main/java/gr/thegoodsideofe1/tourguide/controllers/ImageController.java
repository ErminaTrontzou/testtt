package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.services.ImageService;
import gr.thegoodsideofe1.tourguide.services.TagService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.transaction.Transactional;
import java.util.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/images")
@ResponseBody
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagService tagService;


    @Transactional
    @GetMapping("")
    public List<Image> list(){
        return imageService.listAllImages();
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Image> get (@PathVariable Integer id){
        try{
            Image image = imageService.getImage(id);
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<?> imageByTitle(@PathVariable String title){
        int imagesCount = imageService.getImageCount(title);
        if (imagesCount != 0){
            List<Image> imageResponse = imageService.getImageByTitle(title);
            return new ResponseEntity<List<Image>>(imageResponse, HttpStatus.OK);
        }
        return new ResponseEntity<Image>(HttpStatus.NO_CONTENT);
    }
}

