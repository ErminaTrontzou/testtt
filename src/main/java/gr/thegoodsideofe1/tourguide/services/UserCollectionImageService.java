package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.entities.UserCollectionImage;
import gr.thegoodsideofe1.tourguide.repositories.UserCollectionImageRepository;
import gr.thegoodsideofe1.tourguide.repositories.UserCollectionRepository;
import gr.thegoodsideofe1.tourguide.responses.UserCollectionResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserCollectionImageService {
    @Autowired
    UserCollectionImageRepository userCollectionImagesRepository;
    @Autowired
    UserCollectionRepository userCollectionRepository;
    @Autowired
    AES_ENCRYPTION aes_encryption;
    @Autowired
    UserService userService;
    UserCollectionResponses userCollectionResponses = new UserCollectionResponses();

    public ResponseEntity<?> addImageToCollection(Map<String, String> requestBody){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    //User is Logged IN
                    if (!isBodyValid(requestBody)){
                        return new ResponseEntity<>(checkMissingField(requestBody), HttpStatus.OK);
                    }
                    if (!userCollectionRepository.existsById(Integer.parseInt(requestBody.get("user_collection_id")))){
                        return new ResponseEntity<>(userCollectionResponses.collectionDoesNotExists(), HttpStatus.OK);
                    }
                    UserCollection userCollection = userCollectionRepository.findById(Integer.parseInt(requestBody.get("user_collection_id"))).get();
                    if (loginUser.getIsAdmin() || userCollection.getUser_id().getId() == loginUser.getId()) {
                        //User is Admin or user is owner of the collection
                        userCollectionImagesRepository.save(generateNewUserCollectionImage(requestBody));
                        return new ResponseEntity<>(userCollectionResponses.collectionImageAddedSuccessfully(), HttpStatus.CREATED);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userCollectionResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userCollectionResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userCollectionResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userCollectionResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> removeImageFromCollection(Map<String, String> requestBody, Integer collectionImageID){
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    //User is Logged IN
                    if (!userCollectionImagesRepository.existsById(collectionImageID)) {
                        return new ResponseEntity<>(userCollectionResponses.collectionImageDoesNotExists(), HttpStatus.OK);
                    }
                    UserCollectionImage userCollectionImage = userCollectionImagesRepository.findById(collectionImageID).get();
                    UserCollection userCollection = userCollectionRepository.findById(userCollectionImage.getUserCollectionId()).get();
                    if (loginUser.getIsAdmin() || userCollection.getUser_id().getId() == loginUser.getId()){
                        //User is Admin or owner of the collection
                        userCollectionImagesRepository.deleteById(collectionImageID);
                        return new ResponseEntity<>(userCollectionResponses.collectionImageDeleted(), HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(userCollectionResponses.noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userCollectionResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userCollectionResponses.userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(userCollectionResponses.noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    protected String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }

    protected boolean isBodyValid(Map<String, String> requestBody){
        try {
            requestBody.get("image_id");
            requestBody.get("user_collection_id");
        } catch (ClassCastException | NullPointerException e){
            return false;
        }
        return true;
    }

    protected HashMap<String, String> checkMissingField(Map<String, String> requestBody){
        if (!requestBody.containsKey("image_id")){
            return userCollectionResponses.missingBodyField("image_id");
        }
        if (!requestBody.containsKey("user_collection_id")){
            return userCollectionResponses.missingBodyField("user_collection_id");
        }
        return userCollectionResponses.generalError();
    }

    protected UserCollectionImage generateNewUserCollectionImage(Map<String, String> requestBody){
        UserCollectionImage newUserCollectionsImage = new UserCollectionImage();
        newUserCollectionsImage.setImageId(Integer.parseInt(requestBody.get("image_id")));
        newUserCollectionsImage.setUserCollectionId(Integer.parseInt(requestBody.get("user_collection_id")));
        return newUserCollectionsImage;
    }
}
