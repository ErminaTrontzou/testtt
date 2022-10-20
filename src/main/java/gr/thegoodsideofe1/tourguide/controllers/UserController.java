package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> list(){
        return userService.listAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get (@PathVariable Integer id){
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id){
        try {
            User existUser = userService.getUser(id);
            user.setId(id);
            userService.saveUser(user);
            User updatedUser = userService.getUser(id);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        User deletedUser = userService.getUser(id);
        userService.deleteUser(id);
        return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
    }
}