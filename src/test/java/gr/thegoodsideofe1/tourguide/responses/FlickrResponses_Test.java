package gr.thegoodsideofe1.tourguide.responses;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FlickrResponses_Test {
    FlickrResponses flickrResponses = new FlickrResponses();

    @Test
    void problemOnImageImport() {
        HashMap<String, String> returnedHashMap = flickrResponses.problemOnImageImport();
        if (!returnedHashMap.containsKey("status")){
            assertTrue(false, "Problem On HashMap");
        }
        if (!returnedHashMap.containsKey("message")){
            assertTrue(false, "Problem On HashMap");
        }
    }

    @Test
    void imageSaved() {
        HashMap<String, String> returnedHashMap = flickrResponses.imageSaved();
        if (!returnedHashMap.containsKey("status")){
            assertTrue(false, "Problem On HashMap");
        }
        if (!returnedHashMap.containsKey("message")){
            assertTrue(false, "Problem On HashMap");
        }
    }
}