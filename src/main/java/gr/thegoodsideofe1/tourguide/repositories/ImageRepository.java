package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    public List<Image> findAll();
}
