package gr.thegoodsideofe1.tourguide.responses;

import java.util.HashMap;

public class ImageResponses {
    public HashMap<String, String> noImageFound(){
        HashMap<String, String> returnHashMap = new HashMap<>();
        returnHashMap.put("status", "error");
        returnHashMap.put("message", "No images with your search criteria");
        return returnHashMap;
    }

    public HashMap<String, String> noImageWithID(String id){
        HashMap<String, String> returnHashMap = new HashMap<>();
        returnHashMap.put("status", "error");
        returnHashMap.put("message", "No images with '" + id + "' id.");
        return returnHashMap;
    }
}
