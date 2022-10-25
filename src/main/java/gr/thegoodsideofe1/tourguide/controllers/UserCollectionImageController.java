package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.services.UserCollectionImageService;
import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestBody Map<String, String> requestBody) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

        if (loginUser != null) {
            if (loginUser.getIs_admin()) {
                List<UserCollectionImage> allCollectionImages = userCollectionImageService.listAllCollectionsImages();
                for (UserCollectionImage collectionImage : allCollectionImages){
                    JSONObject singleCollection = new JSONObject();
                    singleCollection.put("id", collectionImage.getId());
                    singleCollection.put("image_id", collectionImage.getImage_id());
                    singleCollection.put("user_collection_id", collectionImage.getUser_collection_id());
                    arrayToReturn.put(singleCollection);
                }
            } else {
                //User is NOT Admin
                JSONObject singleCollection = new JSONObject();
                singleCollection.put("status", "error");
                singleCollection.put("message", "Logged In User is NOT Admin");
                arrayToReturn.put(singleCollection);
            }
        } else {
            JSONObject singleCollection = new JSONObject();
            singleCollection.put("status", "error");
            singleCollection.put("message", "You must provide JWT first");
            arrayToReturn.put(singleCollection);
        }
        return arrayToReturn.toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String specific(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
        UserCollectionImage userCollection = userCollectionImageService.getCollectionImage(id);

        if (loginUser != null) {
            if (loginUser.getIs_admin()) {
                JSONObject singleCollection = new JSONObject();
                singleCollection.put("id", userCollection.getId());
                singleCollection.put("image_id", userCollection.getImage_id());
                singleCollection.put("user_collection_id", userCollection.getUser_collection_id());
                arrayToReturn.put(singleCollection);
            } else {
                //User is NOT Admin
                JSONObject singleCollection = new JSONObject();
                singleCollection.put("status", "error");
                singleCollection.put("message", "Logged In User is NOT Admin or owner of this collection");
                arrayToReturn.put(singleCollection);
            }
        } else {
            JSONObject singleCollection = new JSONObject();
            singleCollection.put("status", "error");
            singleCollection.put("message", "You must provide JWT first");
            arrayToReturn.put(singleCollection);
        }
        return arrayToReturn.toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

//        if (loginUser != null) {
//            //User is Logged In
//            UserCollectionImage userCollectionImage = userCollectionImageService.getCollectionImage(Integer.parseInt(requestBody.get("user_collection_id")));
//            if (userCollectionImage != null) {
//                //User Collection Exists
//                UserCollection userCollection = userCollectionService.getCollection(userCollectionImage.getUser_collection_id());
//                if (userCollection.getUser_id() == loginUser.getId()){
//                    //Collection is owned by the logged in user
//                    userCollectionImage.setUser_collection_id(Integer.parseInt(requestBody.get("user_collection_id")));
//                    userCollectionImage.setImage_id(Integer.parseInt(requestBody.get("image_id")));
//                    userCollectionImageService.saveCollection(userCollectionImage);
//
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "success");
//                    singleCollection.put("message", "User Collection Image Update Successfully");
//                    arrayToReturn.put(singleCollection);
//                } else {
//                    //Collection is NOT owned by the logged in user
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "error");
//                    singleCollection.put("message", "User Collection does owned by another user !");
//                    arrayToReturn.put(singleCollection);
//                }
//            } else {
//                //User Collection Does NOT Exists
//                JSONObject singleCollection = new JSONObject();
//                singleCollection.put("status", "error");
//                singleCollection.put("message", "User Collection does NOT exists !");
//                arrayToReturn.put(singleCollection);
//            }
//        } else {
//            // User is NOT logged in
//            JSONObject singleCollection = new JSONObject();
//            singleCollection.put("status", "error");
//            singleCollection.put("message", "You must provide JWT first");
//            arrayToReturn.put(singleCollection);
//        }
        return arrayToReturn.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Map<String, String> requestBody) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

//        if (loginUser != null) {
//            //User is Logged In
//            UserCollection userCollection = userCollectionService.getCollection(Integer.parseInt(requestBody.get("user_collection_id")));
//            if (userCollection != null){
//                //Collection Exists
//                User userCollectionDetails = userService.getUser(userCollection.getUser_id());
//                if (userCollectionDetails.getId() == loginUser.getId()){
//                    //User Collection owned by logged-in user
//                    UserCollectionImage newCollectionImage = new UserCollectionImage();
//                    newCollectionImage.setImage_id(Integer.parseInt(requestBody.get("image_id")));
//                    newCollectionImage.setUser_collection_id(Integer.parseInt(requestBody.get("user_collection_id")));
//                    userCollectionImageService.saveCollection(newCollectionImage);
//
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "success");
//                    singleCollection.put("message", "Image Added to Collection");
//                    arrayToReturn.put(singleCollection);
//                } else {
//                    //User Collection owned by OTHER user
//                    JSONObject singleCollection = new JSONObject();
//                    singleCollection.put("status", "error");
//                    singleCollection.put("message", "Collection is owned by another user.");
//                    arrayToReturn.put(singleCollection);
//                }
//            } else {
//                //Collection Does NOT Exists
//                JSONObject singleCollection = new JSONObject();
//                singleCollection.put("status", "error");
//                singleCollection.put("message", "Collection does NOT exists.");
//                arrayToReturn.put(singleCollection);
//            }
//        } else {
//            //Collection Does NOT Exists
//            JSONObject singleCollection = new JSONObject();
//            singleCollection.put("status", "error");
//            singleCollection.put("message", "You must provide JWT first");
//            arrayToReturn.put(singleCollection);
//        }
        return arrayToReturn.toString();
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
