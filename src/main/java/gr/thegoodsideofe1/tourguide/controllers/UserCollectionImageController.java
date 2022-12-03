package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.services.UserCollectionImageService;
import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-collection-images")
@CrossOrigin(origins = "*")
public class UserCollectionImageController {

    @Autowired
    UserCollectionImageService userCollectionImageService;
    @Autowired
    UserCollectionService userCollectionService;
    @Autowired
    UserService userService;
    @Autowired
    AES_ENCRYPTION aes_encryption;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Map<String, String> requestBody) {
        return userCollectionImageService.addImageToCollection(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String remove(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

//        if (loginUser != null) {
//            //User is Logged In
//            UserCollectionImage userCollectionImage = userCollectionImageService.getCollectionImage(id);
//            UserCollection userCollection = userCollectionService.getCollection(userCollectionImage.getUser_collection_id());
//            if (userCollection != null) {
//                //Collection Exists
//                User userCollectionDetails = userService.getUser(userCollection.getUser_id());
//                if (userCollectionDetails.getId() == loginUser.getId()) {
//                    //Logged in user is same as collection owner
//                    userCollectionImageService.deleteCollectionImage(id);
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "success");
//                    singleCollection.put("message", "Collection Image deleted successfully.");
//                    arrayToReturn.put(singleCollection);
//                } else {
//                    //Logged in user is NOT same as collection owner
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "error");
//                    singleCollection.put("message", "Collection is owned by another user.");
//                    arrayToReturn.put(singleCollection);
//                }
//            } else {
//                //Collection Does NOT exists
//                JSONObject singleCollection = new JSONObject();
//                singleCollection.put("status", "error");
//                singleCollection.put("message", "You User Collection does NOT exists !");
//                arrayToReturn.put(singleCollection);
//            }
//        } else {
//            //User is NOT logged in
//            JSONObject singleCollection = new JSONObject();
//            singleCollection.put("status", "error");
//            singleCollection.put("message", "You must provide JWT first");
//            arrayToReturn.put(singleCollection);
//        }
        return arrayToReturn.toString();
    }

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
