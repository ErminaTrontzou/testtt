package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.services.ImageService;
import gr.thegoodsideofe1.tourguide.services.ImageTagsService;
import gr.thegoodsideofe1.tourguide.services.TagService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    ImageTagsService imageTagsService;

    @Autowired
    TagService tagService;

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

    @GetMapping("/test")
    public String test (){
        JSONArray array = new JSONArray();
        List<Image> allImages = imageService.listAllImages();
        for (Image allImage : allImages) {
            JSONObject singleImage = new JSONObject();
            singleImage.put("id", String.valueOf(allImage.getId()));
            singleImage.put("description", allImage.getDescription());
            singleImage.put("file_name", allImage.getFile_name());
            singleImage.put("title", allImage.getTitle());
            singleImage.put("latitude", allImage.getLatitude());
            singleImage.put("longitude", allImage.getLongitude());
            singleImage.put("views", String.valueOf(allImage.getViews()));
            singleImage.put("owner_name", allImage.getOwner_name());
            singleImage.put("date_taken", allImage.getDate_taken());

            List<ImageTags> allTags = imageTagsService.getImageTagsByImageID(allImage.getId());
            JSONArray tagsArray = new JSONArray();
            for (ImageTags tag : allTags){
                Tag singleTagInfo = tagService.getTag(tag.getId());
                JSONObject singleTag = new JSONObject();
                singleTag.put("id", tag.getId());
                singleTag.put("image_id", tag.getImage_id());
                singleTag.put("tag_id", tag.getTag_id());
                singleTag.put("name", singleTagInfo.getName());
                tagsArray.put(singleTag);
            }
            singleImage.put("tags", tagsArray);
            array.put(singleImage);
        }
        return array.toString();
    }
}

