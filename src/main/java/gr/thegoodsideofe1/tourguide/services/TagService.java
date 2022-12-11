package gr.thegoodsideofe1.tourguide.services;


import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    public ResponseEntity<?> listAllTags(){
        return new ResponseEntity<>(tagRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<?> getTag( int id){
        try{
            Tag tag = tagRepository.findById(id).get();
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
        }
    }

    public long getByTagName(String name) {
        return tagRepository.getByTagName(name);
    }

    public List<Tag> getTagByTagName(String nameToSearch){
        return tagRepository.getTagByTagName(nameToSearch);
    }

    public void save(Tag tag){
        tagRepository.save(tag);
    }
}
