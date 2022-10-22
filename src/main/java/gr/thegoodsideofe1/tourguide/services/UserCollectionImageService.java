package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.repositories.UserCollectionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserCollectionImageService {
    @Autowired
    UserCollectionImageRepository userCollectionImagesRepository;

    public List<UserCollectionImage> listAllCollectionsImages(){
        return userCollectionImagesRepository.findAll();
    }

    public void saveCollection(UserCollectionImage collection){
        userCollectionImagesRepository.save(collection);
    }

    public UserCollectionImage getCollectionImage(Integer id){
        return userCollectionImagesRepository.findById(id).get();
    }

    public void deleteCollectionImage(Integer id){
        userCollectionImagesRepository.deleteById(id);
    }

    public List<UserCollectionImage> getAllCollectionImagesByCollectionID (Integer collectionID){
        return userCollectionImagesRepository.getAllByCollectionID(collectionID);
    }
}
