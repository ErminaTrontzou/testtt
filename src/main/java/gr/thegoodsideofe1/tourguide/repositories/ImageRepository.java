package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    public List<Image> findAll();

    @Query(value = "SELECT i FROM Image i WHERE i.title LIKE %:title%")
    List<Image> findAllImagesByTitle(@Param("title") String title);

}
