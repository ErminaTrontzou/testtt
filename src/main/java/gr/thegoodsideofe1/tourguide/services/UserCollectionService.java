package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.repositories.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserCollectionService {
    @Autowired
    UserCollectionRepository userCollectionRepository;

    public List<UserCollection> listAllCollections(){
        return userCollectionRepository.findAll();
    }

    public void saveCollection(UserCollection collection){
        userCollectionRepository.save(collection);
    }

    public UserCollection getCollection(Integer id){
        return userCollectionRepository.findById(id).get();
    }

    public void deleteCollection(Integer id){
        userCollectionRepository.deleteById(id);
    }
}
