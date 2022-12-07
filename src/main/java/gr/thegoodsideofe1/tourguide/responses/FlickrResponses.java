package gr.thegoodsideofe1.tourguide.responses;

import java.util.HashMap;

public class FlickrResponses {
    public HashMap<String, String> problemOnImageImport(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "Problem on image import");
        return hashMapToReturn;
    }

    public HashMap<String, String> imageSaved(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "success");
        hashMapToReturn.put("message", "Image Saved Successfully");
        return hashMapToReturn;
    }
}
