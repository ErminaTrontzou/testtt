package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import gr.thegoodsideofe1.tourguide.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-collections")
@CrossOrigin(origins = "*")
public class UserCollectionController {
    @Autowired
    UserCollectionService userCollectionService;
    @Autowired
    private AES_ENCRYPTION aes_encryption;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(@RequestBody Map<String, String> requestBody) throws Exception {
        JSONArray arrayToReturn = new JSONArray();

        String requestJWTToken = requestBody.get("Bearer");
        String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
        User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);

        if (loginUser != null) {
            if (loginUser.getIs_admin()){
                //JWT is Admin
                List<UserCollection> allUserCollections = userCollectionService.listAllCollections();
                for (UserCollection userCollection : allUserCollections){
                    JSONObject singleCollection = new JSONObject();
                    singleCollection.put("id", userCollection.getId());
                    singleCollection.put("name", userCollection.getName());
                    singleCollection.put("description", userCollection.getDescription());
                    singleCollection.put("public", userCollection.getIs_public());
                    singleCollection.put("user_id", userCollection.getUser_id());

                    JSONArray userDetailsDB = new JSONArray();
                    User collectionUser = userService.getUser(userCollection.getUser_id());
                    JSONObject singleUserObj = new JSONObject();
                    singleUserObj.put("id", collectionUser.getId());
                    singleUserObj.put("first_name", collectionUser.getFirst_name());
                    singleUserObj.put("last_name", collectionUser.getLast_name());
                    singleUserObj.put("email", collectionUser.getEmail());
                    singleUserObj.put("username", collectionUser.getUsername());
                    singleUserObj.put("disabled", collectionUser.getDisabled());
                    singleUserObj.put("is_admin", collectionUser.getIs_admin());
                    userDetailsDB.put(singleUserObj);

                    singleCollection.put("user_details", userDetailsDB);

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

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
