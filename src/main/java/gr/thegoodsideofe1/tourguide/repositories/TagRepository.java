package gr.thegoodsideofe1.tourguide.repositories;


import gr.thegoodsideofe1.tourguide.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    public List<Tag> findAll();
}
