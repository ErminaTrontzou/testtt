package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.services.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-collections")
@ResponseBody
public class UserCollectionController {
    @Autowired
    UserCollectionService userCollectionService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> list(@RequestBody Map<String, String> requestBody) {
        return userCollectionService.listAllCollections(requestBody);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> specific(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.getSpecificCollection(requestBody, id);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.updateSpecificCollection(requestBody, id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Map<String, String> requestBody) {
        return userCollectionService.addNewCollection(requestBody);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> delete(@RequestBody Map<String, String> requestBody, @PathVariable Integer id) {
        return userCollectionService.deleteCollection(requestBody, id);
    }
}
