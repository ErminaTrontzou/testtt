package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.repositories.ImageTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImageTagsService {
    @Autowired
    private ImageTagsRepository imageTagsRepository;
    public List<ImageTags> listAllImageTags(){
        return imageTagsRepository.findAll();
    }

    public ImageTags getImageTags(Integer id){
        return imageTagsRepository.findById(id).get();
    }

    public List<ImageTags> getImageTagsByImageID(Integer imageID){
        return imageTagsRepository.findByImageID(imageID);
    }
}
