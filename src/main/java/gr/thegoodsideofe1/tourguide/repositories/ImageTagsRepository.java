package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageTagsRepository extends JpaRepository<ImageTags, Integer> {
    public List<ImageTags> findAll();
}
