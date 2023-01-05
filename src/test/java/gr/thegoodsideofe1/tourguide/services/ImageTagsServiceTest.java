package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.ImageTags;
import gr.thegoodsideofe1.tourguide.repositories.ImageTagsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageTagsServiceTest {

    @Mock
    private ImageTagsRepository imageTagsRepository;

    @InjectMocks
    private ImageTagsService imageTagsService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    ImageTags firstImageTag = new ImageTags(1, 1, 1);
    ImageTags secondImageTag = new ImageTags(2, 2, 2);
    ImageTags thirdImageTag = new ImageTags(3, 3, 3);
    ImageTags fourthImageTag = new ImageTags(4,4,2);

    @Test
    public void getAllImageTagsFromRepository() {
        List<ImageTags> mockImageTags = new ArrayList<>();
        mockImageTags.add(firstImageTag);
        mockImageTags.add(secondImageTag);
        mockImageTags.add(thirdImageTag);
        mockImageTags.add(fourthImageTag);

        when(imageTagsRepository.findAll()).thenReturn(mockImageTags);
        final ResponseEntity<List<ImageTags>> result = (ResponseEntity<List<ImageTags>>) imageTagsService.listAllImageTags();
        assertThat(result).isNotNull();
        assertEquals(200,result.getStatusCodeValue());
        assertEquals(4, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    public void getAllImageTagWithImageIDFromRepository(){
        List<ImageTags> mockImageTags = new ArrayList<>();
        mockImageTags.add(secondImageTag);
        mockImageTags.add(fourthImageTag);

        when(imageTagsRepository.findByImageID(secondImageTag.getImageId())).thenReturn(mockImageTags);
        final List<ImageTags> result = (List<ImageTags>) imageTagsService.getImageTagsByImageID(secondImageTag.getImageId());
        assertThat(result).isNotNull();
        assertEquals(2,result.size());
    }

    @Test
    public void getImageTagWithIdFromRepository(){
        when(imageTagsRepository.findById(firstImageTag.getId())).thenReturn(Optional.ofNullable(firstImageTag));
        final ResponseEntity<ImageTags> result = (ResponseEntity<ImageTags>) imageTagsService.getImageTags(firstImageTag.getId());
        assertThat(result).isNotNull();
        assertEquals(200,result.getStatusCodeValue());
        assertEquals(1, Objects.requireNonNull(result.getBody()).getId());
    }

    @Test
    public  void noContentForGetImageTagByIdFromRepository(){
        when(imageTagsRepository.findById(firstImageTag.getId())).thenThrow(new NoSuchElementException());
        final ResponseEntity<ImageTags> result = (ResponseEntity<ImageTags>) imageTagsService.getImageTags(firstImageTag.getId());
        assertThat(result).isNotNull();
        assertEquals(404,result.getStatusCodeValue());
    }

}
