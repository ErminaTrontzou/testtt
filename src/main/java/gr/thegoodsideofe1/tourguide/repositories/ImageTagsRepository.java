package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageTagsRepository extends JpaRepository<ImageTags, Integer> {
    public List<ImageTags> findAll();

    @Query(value = "SELECT it FROM ImageTags it WHERE it.imageId=:imageID")
    public List<ImageTags> findByImageID(@Param("imageID") Integer imageID);
}
