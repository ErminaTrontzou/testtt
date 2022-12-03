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
    public ResponseEntity<Object> delete(@RequestBody Map<String, String> requestBody, @PathVariable String id) throws Exception {
//        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
//            String requestJWTToken = requestBody.get("Bearer");
//            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
//            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
//
//            if (loginUser != null) {
//                //User is Logged in
//                UserCollection userCollection = userCollectionService.getCollection(Integer.valueOf(id));
//                if (loginUser.getIsAdmin() || userCollection.getUser_id().getId() == loginUser.getId()) {
//                    //User is Admin or is Owner of the User Collection
//                    //Delete Child
//                    List<UserCollectionImage> allCollectionImages = userCollectionImageService.getAllCollectionImagesByCollectionID(userCollection.getId());
//                    for (UserCollectionImage collectionImage : allCollectionImages){
//                        userCollectionImageService.deleteCollectionImage(collectionImage.getId());
//                    }
//                    return Responder.generateResponse("User Collection Deleted", HttpStatus.OK, Object.class);
//                } else {
//                    //User is NOT Admin
//                    return Responder.generateResponse("You have no access to this information", HttpStatus.UNAUTHORIZED, Object.class);
//                }
//            } else {
//                //User is NOT logged in
//                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
//            }
//        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
