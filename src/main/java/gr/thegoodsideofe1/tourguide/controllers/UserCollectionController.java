package gr.thegoodsideofe1.tourguide.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.services.UserCollectionImageService;
import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-collections")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<Object> list(@RequestBody Map<String, String> requestBody) throws Exception {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

            if (loginUser != null) {
                //User is Logged in
                if (loginUser.getIs_admin()) {
                    //User is Admin
                    List<UserCollection> allUserCollection = userCollectionService.listAllCollections();
                    List<JSONObject> allUserCollectionEntities = new ArrayList<JSONObject>();
                    for (UserCollection userCollection : allUserCollection) {
                        JSONObject collection = new JSONObject();
                        collection.put("id", userCollection.getId());
                        collection.put("name", userCollection.getName());
                        collection.put("description", userCollection.getDescription());
                        collection.put("public", userCollection.getIs_public());
                        collection.put("user_id", userCollection.getUser_id());
                    }
                    return Responder.generateResponse("success", HttpStatus.OK, allUserCollection.toArray());
                } else {
                    //User is NOT Admin
                    return Responder.generateResponse("You have no access to this information", HttpStatus.UNAUTHORIZED, Object.class);
                }
            } else {
                //User is NOT logged in
                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
            }
        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> specific(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) throws Exception {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

            if (loginUser != null) {
                //User is Logged in
                UserCollection userCollection = userCollectionService.getCollection(id);
                if (loginUser.getIs_admin() || userCollection.getUser_id().getId() == loginUser.getId()) {
                    //User is Admin or is Owner of the User Collection
                    return Responder.generateResponse("success", HttpStatus.OK, userCollection);
                } else {
                    //User is NOT Admin
                    return Responder.generateResponse("You have no access to this information", HttpStatus.UNAUTHORIZED, Object.class);
                }
            } else {
                //User is NOT logged in
                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
            }
        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) throws Exception {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

            if (loginUser != null) {
                //User is Logged in
                UserCollection userCollection = userCollectionService.getCollection(id);
                if (loginUser.getIs_admin() || userCollection.getUser_id().getId() == loginUser.getId()) {
                    //User is Admin or is Owner of the User Collection
                     if (!requestBody.get("name").isBlank()){
                        userCollection.setName(requestBody.get("name"));
                    }
                    if (!requestBody.get("description").isEmpty()){
                        userCollection.setName(requestBody.get("description"));
                    }
                    if (!requestBody.get("public").isEmpty()){
                        userCollection.setIs_public(Boolean.getBoolean(requestBody.get("public")));
                    }
                    userCollectionService.saveCollection(userCollection);
                    return Responder.generateResponse("success", HttpStatus.OK, userCollection);
                } else {
                    //User is NOT Admin
                    return Responder.generateResponse("You have no access to this information", HttpStatus.UNAUTHORIZED, Object.class);
                }
            } else {
                //User is NOT logged in
                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
            }
        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Map<String, String> requestBody) throws Exception {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

            if (loginUser != null) {
                //User is Logged in
                UserCollection newUserCollection = new UserCollection();
                newUserCollection.setId(6);
                newUserCollection.setName(requestBody.get("name"));
                newUserCollection.setDescription(requestBody.get("description"));
                newUserCollection.setUser_id(loginUser);
                newUserCollection.setIs_public(Boolean.getBoolean(requestBody.get("public")));
                userCollectionService.saveCollection(newUserCollection);
                return Responder.generateResponse("success", HttpStatus.OK, newUserCollection);
            } else {
                //User is NOT logged in
                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
            }
        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestBody Map<String, String> requestBody, @PathVariable String id) throws Exception {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
            User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

            if (loginUser != null) {
                //User is Logged in
                UserCollection userCollection = userCollectionService.getCollection(Integer.valueOf(id));
                if (loginUser.getIs_admin() || userCollection.getUser_id().getId() == loginUser.getId()) {
                    //User is Admin or is Owner of the User Collection
                    //Delete Child
                    List<UserCollectionImage> allCollectionImages = userCollectionImageService.getAllCollectionImagesByCollectionID(userCollection.getId());
                    for (UserCollectionImage collectionImage : allCollectionImages){
                        userCollectionImageService.deleteCollectionImage(collectionImage.getId());
                    }
                    return Responder.generateResponse("User Collection Deleted", HttpStatus.OK, Object.class);
                } else {
                    //User is NOT Admin
                    return Responder.generateResponse("You have no access to this information", HttpStatus.UNAUTHORIZED, Object.class);
                }
            } else {
                //User is NOT logged in
                return Responder.generateResponse("Not Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
            }
        }
        return Responder.generateResponse("Provide Valid JWT", HttpStatus.UNAUTHORIZED, Object.class);
    }

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
