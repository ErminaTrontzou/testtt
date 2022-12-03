package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.services.TagService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/v1/flickr")
@ResponseBody
public class FlickrController {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagService tagService;

    @Transactional
    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<?> getNewImages(@PathVariable String title){
        List<Image> imageList = getFlickr(title);
        if(imageList.size()!=0) {
            return new ResponseEntity<>(imageList, HttpStatus.OK);
        }
        HashMap<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("status", "error");
        returnMap.put("message", "Problem on image import");
        return ResponseEntity.status(200).body(returnMap);
    }

    private List<Image> getFlickr(String title) {
        String flickrAPIURl = "https://www.flickr.com/services/rest/";
        flickrAPIURl += "?method=flickr.photos.search";
        flickrAPIURl += "&api_key=d4e4f456722f8f76048260df1e0c1880";
        flickrAPIURl += "&format=json";
        flickrAPIURl += "&text=" + title;
        flickrAPIURl += "&has_geo=1";
        flickrAPIURl += "&privacy_filter=1";
        flickrAPIURl += "&accuracy=11";
        flickrAPIURl += "&content_type=1";
        flickrAPIURl += "&media=photo";
        flickrAPIURl += "&extras=geo, description, media, tags, url_o, url_m,date_upload, date_taken, views, owner_name";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(flickrAPIURl)
                .addHeader("Accept", "application/json")
                .addHeader("charset", "utf-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject flickResponse = convertFlickrResponseToJSON(response.body().string());
            return serializeFlickrResponseData(flickResponse);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private List<Image> serializeFlickrResponseData(JSONObject allData) {
        JSONObject photosObj = new JSONObject(allData.get("photos").toString());
        JSONArray photoArray = new JSONArray(photosObj.get("photo").toString());
        List<Image> photoList = new ArrayList<>();

        for (int i = 0; i<photoArray.length(); i++){
            JSONObject singlePhotoObj = new JSONObject(photoArray.get(i).toString());
            JSONObject singlePhotoDescription = new JSONObject(singlePhotoObj.get("description").toString());

            Image imageToSend = new Image();
            try {
                imageToSend.setId(Long.parseLong(singlePhotoObj.getString("id")));
                imageToSend.setDescription(singlePhotoDescription.getString("_content"));
                imageToSend.setTitle(Jsoup.clean(singlePhotoObj.getString("title"), Safelist.relaxed()));
                imageToSend.setLatitude(singlePhotoObj.getString("latitude"));
                imageToSend.setLongitude(singlePhotoObj.getString("longitude"));
                imageToSend.setFileName(singlePhotoObj.getString("url_o"));
                imageToSend.setDateTaken(singlePhotoObj.getString("datetaken"));
                imageToSend.setViews(Integer.parseInt(singlePhotoObj.getString("views")));
                imageToSend.setOwnerName(singlePhotoObj.getString("ownername"));
                imageToSend.setThumbnail(singlePhotoObj.getString("url_m"));
                Set<Tag> allTagsToSend = serializeTags(singlePhotoObj.getString("tags"));
                imageToSend.setTags(allTagsToSend);
            }catch(JSONException e){
                continue;
            }
            photoList.add(imageToSend);
        }
        return photoList;
    }

    @PostMapping("/imageToSave")
    public ResponseEntity<?>imageToSave(@RequestBody Image photo){
        imageRepository.save(photo);
        return new ResponseEntity<>("Photo Saved",HttpStatus.CREATED);
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

    private Set<Tag> serializeTags(String allTags){
        Set<Tag> imageTagsSet = new HashSet<Tag>();
        if (allTags.isEmpty()){
            return imageTagsSet;
        }
        String[] splitTags = allTags.split(" ");
        for (String tag: splitTags){
            long tagCount = tagService.getByTagName(tag);
            if (tagCount != 0){
                //Tag Exists
                List<Tag> singleTagDB = tagService.getTagByTagName(tag);
                imageTagsSet.add(singleTagDB.get(0));
                continue;
            }
            //Create Tag
            Tag tagToSave = new Tag();
            tagToSave.setName(tag);
            tagService.save(tagToSave);
            imageTagsSet.add(tagToSave);
        }
        return imageTagsSet;
    }
}
