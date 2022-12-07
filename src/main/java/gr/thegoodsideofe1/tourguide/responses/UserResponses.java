package gr.thegoodsideofe1.tourguide.responses;

import java.util.HashMap;

public class UserResponses {
    public HashMap<String, String> noValidJWTResponse(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "Provide Valid JWT");
        return hashMapToReturn;
    }

    public HashMap<String, String> userIsNotLoggedIN(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "User is NOT Logged In");
        return hashMapToReturn;
    }

    public HashMap<String, String> noAccessToInformation() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "You have no access to this information");
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

    public HashMap<String, String> userAddedSuccessfully() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "User Added");
        return hashMapToReturn;
    }

    public HashMap<String, String> userSavedSuccessfully() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "User Saved Successfully");
        return hashMapToReturn;
    }

    public HashMap<String, String> userDeleted() {
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "User Deleted");
        return hashMapToReturn;
    }

    public HashMap<String, String> noSuchUser(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "There is NO Such User");
        return hashMapToReturn;
    }

    public HashMap<String, String> successLogin(String token){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("token", token);
        return hashMapToReturn;
    }

    public HashMap<String, String> userIsNotAdmin(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "User is NOT Admin");
        return hashMapToReturn;
    }

    public HashMap<String, String> userIsAdmin(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "User is Admin");
        return hashMapToReturn;
    }
}
