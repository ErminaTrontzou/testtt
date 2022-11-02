package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCollectionImageRepository extends JpaRepository<UserCollectionImage, Integer> {
    List<UserCollectionImage> findAll();

    @Query(value = "SELECT u FROM UserCollectionImage u WHERE u.userCollectionId=:userCollectionID")
    List<UserCollectionImage> getAllByCollectionID(@Param("userCollectionID") Integer userCollectionID);
}
