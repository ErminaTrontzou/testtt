package gr.thegoodsideofe1.tourguide.controllers;

import com.google.gson.Gson;
import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.services.ImageService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;


import okio.ByteString;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/v1/images")
@ResponseBody
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;


    @GetMapping("")
    public List<Image> list(){
        return imageService.listAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> get (@PathVariable Integer id){
        try{
            Image image = imageService.getImage(id);
            return new ResponseEntity<Image>(image, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getByTitle/{title}")
    public List<Image> imageByTitle(@PathVariable String title){
        List<Image> imageResponse = imageService.getImageByTitle(title);
        if(imageResponse.isEmpty()) {
            if(getFlickr(title)){
                imageResponse = imageService.getImageByTitle(title);
            }
        }
        return imageResponse;
    }

    private boolean getFlickr(String title) {
        String flickrAPIURl = "https://www.flickr.com/services/rest/";
        flickrAPIURl += "?method=flickr.photos.search";
        flickrAPIURl += "&api_key=d4e4f456722f8f76048260df1e0c1880";
        flickrAPIURl += "&format=json";
        flickrAPIURl += "&text=" + title;
        flickrAPIURl += "&has_geo=1";
        flickrAPIURl += "&privacy_filter=1";
        flickrAPIURl += "&accuracy=11";
        flickrAPIURl += "&content_type=1";
        flickrAPIURl += "&media=all";
        flickrAPIURl += "&extras=geo, description, media, tags, url_o, date_upload, date_taken, views, owner_name";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(flickrAPIURl)
                    .addHeader("Accept", "application/json")
                    .addHeader("charset", "utf-8")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                JSONObject flickResponse = convertFlickrResponseToJSON(response.body().string());
                serializeFlickrResponseData(flickResponse);
            } catch (IOException e){
                return false;
            }
            return true;
    }

    private void serializeFlickrResponseData(JSONObject allData){
        JSONObject photosObj = new JSONObject(allData.get("photos").toString());
        JSONArray photoArray = new JSONArray(photosObj.get("photo").toString());

        for (int i = 0; i<photoArray.length(); i++){
            JSONObject singlePhotoObj = new JSONObject(photoArray.get(i).toString());
            JSONObject singlePhotoDescription = new JSONObject(singlePhotoObj.get("description").toString());

            Image imageToSave = new Image();
            imageToSave.setTitle(singlePhotoObj.getString("title"));
            imageToSave.setDescription(singlePhotoDescription.getString("_content"));
            imageToSave.setLatitude(singlePhotoObj.getString("latitude"));
            imageToSave.setLongitude(singlePhotoObj.getString("longitude"));
            imageToSave.setFile_name(singlePhotoObj.getString("url_o"));
            imageToSave.setDate_taken(singlePhotoObj.getString("datetaken"));
            imageToSave.setViews(Integer.parseInt(singlePhotoObj.getString("views")));
            imageToSave.setOwner_name(singlePhotoObj.getString("ownername"));
            //TODO: Save Tags
            imageRepository.save(imageToSave);
        }
    }

    private JSONObject convertFlickrResponseToJSON(String bodyData){
        //Trim Body Data String
        String trimmedBodyData = bodyData.replace("jsonFlickrApi(", "");
        StringBuffer bodyResponseBuffer = new StringBuffer(trimmedBodyData);
        bodyResponseBuffer.deleteCharAt(bodyResponseBuffer.length() - 1);

        //Convert to JSON
        JSONObject jsonObj = new JSONObject(bodyResponseBuffer.toString());
        return jsonObj;
    }

}

