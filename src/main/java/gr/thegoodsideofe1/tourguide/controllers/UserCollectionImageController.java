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
    public ResponseEntity<?> remove(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionImageService.removeImageFromCollection(requestBody, id);
    }

    private String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }
}
