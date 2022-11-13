package gr.thegoodsideofe1.tourguide.repositories;


import gr.thegoodsideofe1.tourguide.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    public List<Tag> findAll();

    @Query(value = "SELECT COUNT(t.id) FROM Tag t WHERE t.name=:nameToSearch")
    public long getByTagName(@Param("nameToSearch") String nameToSearch);

    @Query(value = "SELECT t FROM Tag t WHERE t.name=:nameToSearch")
    public Tag getTagByTagName(@Param("nameToSearch") String nameToSearch);
}
