package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.services.ImageTagsService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ImageController.class)
public class ImageTagsControllerTest {
    @Mock
    private ImageTagsService imageTagsService;

    @InjectMocks
    private ImageTagsController imageTagsController;

    private MockMvc mockMvc;

    ImageTags firstImageTag = new ImageTags(1, 1, 1);
    ImageTags secondImageTag = new ImageTags(2, 2, 2);
    ImageTags thirdImageTag = new ImageTags(3, 3, 3);
    ImageTags fourthImageTag = new ImageTags(4,4,2);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(imageTagsController).build();
    }

    @After
    public void tearDown(){
        this.mockMvc=null;
    }

    @Test
    public void  getAllImageTagsFromService()  throws Exception{
        List<ImageTags> mockImageTags = new ArrayList<>();
        mockImageTags.add(firstImageTag);
        mockImageTags.add(secondImageTag);
        mockImageTags.add(thirdImageTag);
        mockImageTags.add(fourthImageTag);

        when(imageTagsService.listAllImageTags()).thenReturn(new ResponseEntity(mockImageTags, HttpStatus.OK));
        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/image_tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));

    }

    @Test
    public void getMockImagesById() throws Exception{

        when(imageTagsService.getImageTags(firstImageTag.getId())).thenReturn(new ResponseEntity(firstImageTag, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/image_tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tagId", Matchers.is(1)));
    }

}
