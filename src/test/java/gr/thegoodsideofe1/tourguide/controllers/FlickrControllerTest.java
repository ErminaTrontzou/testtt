package gr.thegoodsideofe1.tourguide.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.services.FlickrService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlickrController.class)
public class FlickrControllerTest {

    @InjectMocks
    FlickrController flickrController;

    @Mock
    FlickrService flickrService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    Image firstImage = new Image(1,"url_l1","desc1","thessaloniki","12345","67890",1000,"owner1","dateTaken1","url_t1");
    Image secondImage = new Image(2,"url_l2","desc2","athens","12345","67890",2000,"owner2","dateTaken2","url_t2");
    Image thirdImage = new Image(3,"url_l3","desc3","thessaloniki","12345","67890",3000,"owner3","dateTaken3","url_t3");


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(flickrController).build();
    }

    @Test
    public void getAllImagesFromFlickrAPI() throws Exception {
        List<Image> mockImage = new ArrayList<>();
        mockImage.add(firstImage);
        mockImage.add(secondImage);
        mockImage.add(thirdImage);

        when(flickrService.getNewImagesForLocation(firstImage.getTitle())).thenReturn(new ResponseEntity(firstImage, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/flickr/getByTitle/thessaloniki")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("thessaloniki")));
    }

    @Test
    public void saveAndImageViaFlickrController() throws Exception {
        HashMap<String, String> returnedMessage = new HashMap<>();
        when(flickrController.imageToSave(firstImage)).thenReturn(new ResponseEntity(returnedMessage, HttpStatus.OK));

        String requestJson = objectWriter.writeValueAsString(firstImage);
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/flickr/imageToSave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
}