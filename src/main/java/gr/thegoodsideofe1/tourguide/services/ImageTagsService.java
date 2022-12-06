package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.repositories.ImageTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ImageTagsService {
    @Autowired
    private ImageTagsRepository imageTagsRepository;
    public ResponseEntity<?> listAllImageTags(){
        return new ResponseEntity<>(imageTagsRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getImageTags(Integer id){
        try{
            ImageTags imageTags = imageTagsRepository.findById(id).get();
            return new ResponseEntity<ImageTags>(imageTags, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<ImageTags>(HttpStatus.NOT_FOUND);
        }
    }

    public List<ImageTags> getImageTagsByImageID(Integer imageID){
        return imageTagsRepository.findByImageID(imageID);
    }
}
