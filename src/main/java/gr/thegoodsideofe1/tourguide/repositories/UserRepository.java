package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();

    @Query(value = "SELECT u FROM User u WHERE u.email=:email")
    public User getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT u FROM User u WHERE u.username=:username")
    public User getUserByUsername(@Param("username") String username);

    @Query(value = "SELECT u FROM User u WHERE u.username=:username AND u.email=:email AND u.firstName=:firstName AND u.lastName=:lastName")
    public User getUserByParams(@Param("username") String username, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
