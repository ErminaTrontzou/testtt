package gr.thegoodsideofe1.tourguide.controllers;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.services.ImageService;
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
public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;

    Image firstImageMock = new Image(1,"url_l1","desc1","title1","12345","67890",1000,"owner1","dateTaken1","url_t1");
    Image secondImageMock = new Image(2,"url_l2","desc2","title2","212345","267890",2000,"owner2","dateTaken2","url_t2");
    Image thirdImageMock = new Image(3,"url_l3","desc3","title3","312345","367890",3000,"owner3","dateTaken3","url_t3");

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @After
    public void tearDown(){
        this.mockMvc=null;
    }

    @Test
    public void getAllMockImagesFromService() throws Exception{

        List<Image> mockImage = new ArrayList<>();
        mockImage.add(firstImageMock);
        mockImage.add(secondImageMock);
        mockImage.add(thirdImageMock);

        when(imageService.listAllImages()).thenReturn(new ResponseEntity(mockImage, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/images")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("title3")));
    }

    @Test
    public void getMockImagesById() throws Exception{

        when(imageService.getImage(firstImageMock.getId())).thenReturn(new ResponseEntity(firstImageMock, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/images/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title1")));
    }

    @Test
    public void getMockByTitle() throws Exception{

        when(imageService.getImageByTitle(firstImageMock.getTitle(),1,1)).thenReturn(new ResponseEntity(firstImageMock, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/images/getByTitle/title1?page=1&size=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName", Matchers.is("url_l1")));
    }
}
