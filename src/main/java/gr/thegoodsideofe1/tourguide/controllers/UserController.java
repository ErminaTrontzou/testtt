package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestBody Map<String, String> requestBody){
        return userService.getAllUsers(requestBody);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Integer id, @RequestBody Map<String, String> requestBody){
        return userService.getSpecificUser(id, requestBody);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Map<String, String> requestBody){
        return userService.addNewUser(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Map<String, String> requestBody, @PathVariable Integer id){
        return userService.updateSpecificUser(id, requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id, @RequestBody Map<String, String> requestBody){
        return userService.deleteSpecificUser(requestBody, id);
    }

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> userLoginDetails) {
        return userService.loginUserWithEmail(userLoginDetails);
    }

    @RequestMapping(value = "/login/username", method = RequestMethod.POST)
    public ResponseEntity<?> loginUsername(@RequestBody Map<String, String> userLoginDetails) {
        return userService.loginUserWithUsername(userLoginDetails);
    }

    @RequestMapping(value = "/is-admin", method = RequestMethod.POST)
    public ResponseEntity<?> checkUserIsAdmin(@RequestBody Map<String, String> requestBody){
        return userService.userIsAdmin(requestBody);
    }
}