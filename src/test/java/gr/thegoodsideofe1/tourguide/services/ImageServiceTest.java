package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.responses.ImageResponses;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageResponses imageResponses;

    @Mock
    private Page<Image> imagePage;

    @InjectMocks
    private ImageService imageService;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    Image firstImage = new Image(1, "url_l1", "desc1", "title1", "12345", "67890", 1000, "owner1", "dateTaken1", "url_t1");
    Image secondImage = new Image(2,"url_l2","desc2","title2","212345","267890",2000,"owner2","dateTaken2","url_t2");
    Image thirdImage = new Image(3,"url_l3","desc3","title3","312345","367890",3000,"owner3","dateTaken3","url_t3");



    @Test
    public void getAllMockImagesFromRepository(){

        List<Image> mockImage = new ArrayList<>();
        mockImage.add(firstImage);
        mockImage.add(secondImage);
        mockImage.add(thirdImage);

        when(imageRepository.findAll()).thenReturn(mockImage);
        final ResponseEntity<List<Image>> result = (ResponseEntity<List<Image>>) imageService.listAllImages();
        assertThat(result).isNotNull();
        assertEquals(200,result.getStatusCodeValue());
        assertEquals(3, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    public void getMockImageByIdFromRepository() {
        when(imageRepository.findById(firstImage.getId())).thenReturn(Optional.of(firstImage));
        final ResponseEntity<?> result = imageService.getImage(firstImage.getId());
        assertThat(result).isNotNull();
        assertEquals(firstImage,result.getBody());
        assertEquals(200,result.getStatusCodeValue());
    }
    @Test
    public void noContentForGetMockImageByIdFromRepository() {
        when(imageRepository.findById(firstImage.getId())).thenThrow(new NoSuchElementException());
        final ResponseEntity<?> result = imageService.getImage(firstImage.getId());
        ResponseEntity<HashMap<String,String>> r = (ResponseEntity<HashMap<String, String>>) result;
        final HashMap<String, String> expectedAnswer = imageResponses.noImageWithID(String.valueOf(firstImage.getId()));
        assertEquals(r.getBody(),expectedAnswer);
    }

    @Test
    public void getMockImageByTitleFromRepository(){
        Pageable pageable = PageRequest.of(1, 1);
        Page<Image> images = new PageImpl<>(Collections.singletonList(firstImage));
        when(imageRepository.findAllImagesByTitle(firstImage.getTitle(),pageable)).thenReturn(images);
        when(imageRepository.countImagesByTitle(firstImage.getTitle())).thenReturn(1);
        final ResponseEntity<?> result = imageService.getImageByTitle(firstImage.getTitle(),1,1);
        assertThat(result).isNotNull();
        assertEquals(images,result.getBody());
        assertEquals(200,result.getStatusCodeValue());
    }

    @Test
    public void noContentForGetMockImageByTitleFromRepository(){
        when(imageRepository.countImagesByTitle(firstImage.getTitle())).thenReturn(0);
        final ResponseEntity<?> result = imageService.getImageByTitle(firstImage.getTitle(),1,1);
        assertThat(result).isNotNull();
        assertEquals(204,result.getStatusCodeValue());
    }
}
