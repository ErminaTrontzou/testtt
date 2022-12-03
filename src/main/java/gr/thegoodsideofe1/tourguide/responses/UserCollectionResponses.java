package gr.thegoodsideofe1.tourguide.responses;

import java.util.HashMap;

public class UserCollectionResponses {
    public HashMap<String, String> noValidJWTResponse(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "Provide Valid JWT");
        return hashMapToReturn;
    }

    public HashMap<String, String> userIsNotLoggedIN(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "NOT Valid JWT");
        return hashMapToReturn;
    }

    public HashMap<String, String> noAccessToInformation() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "You have no access to this information");
        return hashMapToReturn;
    }

    public HashMap<String, String> collectionUpdatedSuccessfully() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "Collection Updated");
        return hashMapToReturn;
    }

    public HashMap<String, String> missingBodyField(String missingParam) {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "Field '" + missingParam + "' is missing");
        return hashMapToReturn;
    }

    public HashMap<String, String> generalError() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "There was an error during execution");
        return hashMapToReturn;
    }

    public HashMap<String, String> collectionSavedSuccessfully() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "Collection Saved");
        return hashMapToReturn;
    }

    public HashMap<String, String> collectionDeletedSuccessfully() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "Collection Deleted");
        return hashMapToReturn;
    }
}
