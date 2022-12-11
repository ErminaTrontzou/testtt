package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import gr.thegoodsideofe1.tourguide.responses.ImageResponses;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    Image firstImageMock = new Image(1, "url_l1", "desc1", "title1", "12345", "67890", 1000, "owner1", "dateTaken1", "url_t1");
    Image secondImageMock = new Image(2,"url_l2","desc2","title2","212345","267890",2000,"owner2","dateTaken2","url_t2");
    Image thirdImageMock = new Image(3,"url_l3","desc3","title3","312345","367890",3000,"owner3","dateTaken3","url_t3");


    @Test
    public void getAllMockImagesFromRepository(){

        List<Image> mockImage = new ArrayList<>();
        mockImage.add(firstImageMock);
        mockImage.add(secondImageMock);
        mockImage.add(thirdImageMock);

        when(imageRepository.findAll()).thenReturn(mockImage);
        final ResponseEntity<List<Image>> result = (ResponseEntity<List<Image>>) imageService.listAllImages();
        assertThat(result).isNotNull();
        assertEquals(200,result.getStatusCodeValue());
        assertEquals(3, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    public void getMockImageByIdFromRepository() {
        when(imageRepository.findById(firstImageMock.getId())).thenReturn(Optional.of(firstImageMock));
        final ResponseEntity<?> result = imageService.getImage(firstImageMock.getId());
        assertThat(result).isNotNull();
        assertEquals(firstImageMock,result.getBody());
        assertEquals(200,result.getStatusCodeValue());
    }
    @Test
    public void noContentForGetMockImageByIdFromRepository() {
        when(imageRepository.findById(firstImageMock.getId())).thenThrow(new NoSuchElementException());
        final ResponseEntity<?> result = imageService.getImage(firstImageMock.getId());
        final HashMap<String, String> resultHashMap = imageResponses.noImageWithID(String.valueOf(firstImageMock.getId()));
        assertEquals(200,result.getStatusCodeValue());
    }

//    @Test
//    public void getMockImageByTitleFromRepository(){
//        Pageable pageable = PageRequest.of(1, 1);
//        Page<Image> imagesPage = imageRepository.findAllImagesByTitle(firstImageMock.getTitle(), pageable);
//        when(imagesPage).thenReturn(firstImageMock.getTitle(),pageable);
//        final ResponseEntity<?> result = imageService.getImageByTitle(firstImageMock.getTitle(),1,1);
//        assertThat(result).isNotNull();
//        assertEquals(firstImageMock,result.getBody());
//        assertEquals(200,result.getStatusCodeValue());
//    }
}
