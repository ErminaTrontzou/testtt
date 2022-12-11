package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.responses.ImageResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    ImageResponses imageResponses = new ImageResponses();
    public ResponseEntity<?> listAllImages(){
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getImage(Long id){
        try {
            Image image = imageRepository.findById(id).get();
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(imageResponses.noImageWithID(id.toString()), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getImageByTitle(String title, Integer page, Integer size){
        int imagesCount = imageRepository.countImagesByTitle(title);
        if (imagesCount != 0){
            Pageable paging = PageRequest.of(page, size);
            Page<Image> imagesPage = imageRepository.findAllImagesByTitle(title, paging);
            return new ResponseEntity<>(imagesPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(imageResponses.noImageFound(), HttpStatus.NO_CONTENT);
    }

}
