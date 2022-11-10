package gr.thegoodsideofe1.tourguide.services;


import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    public List<Tag> listAllTags(){
        return tagRepository.findAll();
    }

    public Tag getTag(Integer id){
        return tagRepository.findById(id).get();
    }

    public long getByTagName(String name) {
        return tagRepository.getByTagName(name);
    }

    public Tag getTagByTagName(String nameToSearch){
        return tagRepository.getTagByTagName(nameToSearch);
    }

    public void save(Tag tag){
        tagRepository.save(tag);
    }
}
