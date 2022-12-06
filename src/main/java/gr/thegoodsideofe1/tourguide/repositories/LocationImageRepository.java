package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.LocationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationImageRepository extends JpaRepository<LocationImage, Integer> {
    public List<LocationImage> findAll();
}
