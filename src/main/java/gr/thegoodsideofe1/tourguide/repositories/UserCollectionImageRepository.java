package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCollectionImageRepository extends JpaRepository<UserCollectionImage, Integer> {
    List<UserCollectionImage> findAll();
}
