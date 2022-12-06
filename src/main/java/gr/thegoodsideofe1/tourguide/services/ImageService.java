package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public ResponseEntity<?> listAllImages(){
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getImage( Long id){
        try{
            Image image = imageRepository.findById(id).get();
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getImageByTitle(String title,
                                       @RequestParam(value="page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "8") int size){
        int imagesCount = imageRepository.countImagesByTitle(title);
        if (imagesCount != 0){
            Pageable paging = PageRequest.of(page, size);
            Page<Image> imagesPage = imageRepository.findAllImagesByTitle(title, paging);
            return new ResponseEntity<>(imagesPage, HttpStatus.OK);
        }
        HashMap<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("status", "error");
        returnMap.put("message", "No images with your search criteria");
        return ResponseEntity.status(204).body(returnMap);
    }

}
