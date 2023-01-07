package gr.thegoodsideofe1.tourguide.repositories;


import gr.thegoodsideofe1.tourguide.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    List<Tag> findAll();

    @Query(value = "SELECT COUNT(t.id) FROM Tag t WHERE t.name=:nameToSearch")
    long getByTagName(@Param("nameToSearch") String nameToSearch);

    @Query(value = "SELECT t FROM Tag t WHERE t.name=:nameToSearch")
    List<Tag> getTagByTagName(@Param("nameToSearch") String nameToSearch);
}
