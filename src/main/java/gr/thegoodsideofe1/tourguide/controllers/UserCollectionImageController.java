package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.services.UserCollectionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-collection-images")
@CrossOrigin(origins = "*")
public class UserCollectionImageController {

    @Autowired
    UserCollectionImageService userCollectionImageService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Map<String, String> requestBody) {
        return userCollectionImageService.addImageToCollection(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> remove(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionImageService.removeImageFromCollection(requestBody, id);
    }
}
