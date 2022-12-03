package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.services.UserCollectionImageService;
import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user-collections")
@ResponseBody
public class UserCollectionController {
    @Autowired
    UserCollectionService userCollectionService;
    @Autowired
    UserCollectionImageService userCollectionImageService;
    @Autowired
    private AES_ENCRYPTION aes_encryption;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestBody Map<String, String> requestBody) {
        return userCollectionService.listAllCollections(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> specific(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.getSpecificCollection(requestBody, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.updateSpecificCollection(requestBody, id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Map<String, String> requestBody) {
        return userCollectionService.addNewCollection(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.deleteCollection(requestBody, id);
    }

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
