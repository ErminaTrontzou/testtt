package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public List<Image> listAllImages(){
        return imageRepository.findAll();
    }

    public Image getImage(Integer id){
        return imageRepository.findById(id).get();
    }

    public List<Image> getImageByTitle(String title){
        return imageRepository.findAllImagesByTitle(title);
    }

    public int getImageCount(String title){
        return imageRepository.countImagesByTitle(title);
    }
}
