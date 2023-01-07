package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Tag;
import gr.thegoodsideofe1.tourguide.repositories.TagRepository;
import org.hibernate.annotations.Any;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    private Tag tag1 = new Tag(1, "one");
    private Tag tag2 = new Tag(2, "two");
    private Tag tag3 = new Tag(3, "three");
    private Tag tag4 = new Tag(4, "four");
    private Tag tag5duppy = new Tag(5, "duplicate");
    private Tag tag6duppy = new Tag(6, "duplicate");
    private Tag tag7duppy = new Tag(7, "duplicate");
    private List<Tag> allTags = List.of(tag1, tag2, tag3, tag4);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void gettingAllTagsFromRepositoryWithData() {
        when(tagRepository.findAll()).thenReturn(allTags);

        ResponseEntity<List<Tag>> result = (ResponseEntity<List<Tag>>) tagService.listAllTags();

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertEquals(allTags.size(), result.getBody().size());
        assertIterableEquals(allTags, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void gettingAllTagsFromRepositoryThatIsEmpty() {
        List<Tag> emptyList = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(emptyList);

        ResponseEntity<List<Tag>> result = (ResponseEntity<List<Tag>>) tagService.listAllTags();

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertEquals(0, result.getBody().size());
        assertIterableEquals(emptyList, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void gettingAnExistingTagById() {
        when(tagRepository.findById(2)).thenReturn(Optional.of(tag2));

        ResponseEntity<Tag> result = (ResponseEntity<Tag>) tagService.getTag(2);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertEquals(tag2, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void gettingANonExistingTagById() {
        when(tagRepository.findById(2)).thenReturn(Optional.empty());

        ResponseEntity<Tag> result = (ResponseEntity<Tag>) tagService.getTag(2);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNull();
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void gettingACountOfATagWithAGivenNameThatExistsOnce() {
        Long count = 1L;
        when(tagRepository.getByTagName("aTag")).thenReturn(count);

        long result = tagService.getByTagName("aTag");

        assertEquals(count.longValue(), result);
    }

    @Test
    public void gettingACountOfATagWithAGivenNameThatExistsALot() {
        Long count = 324L;
        when(tagRepository.getByTagName("veryDuplicated")).thenReturn(count);

        long result = tagService.getByTagName("veryDuplicated");

        assertEquals(count.longValue(), result);
    }

    @Test
    public void gettingACountOfATagThatDoesntExist() {
        Long count = 0L;
        when(tagRepository.getByTagName("nonExistent")).thenReturn(count);

        long result = tagService.getByTagName("nonExistent");

        assertEquals(count.longValue(), result);
    }


    @Test
    public void gettingASingleTagByNameBecauseItExistsOnce() {
        when(tagRepository.getTagByTagName("one")).thenReturn(List.of(tag1));

        List<Tag> result = tagService.getTagByTagName("one");

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
        assertIterableEquals(List.of(tag1), result);
    }

    @Test
    public void gettingADuplicateTagByNameManyTimes() {
        List<Tag> duplicates = List.of(tag5duppy, tag6duppy, tag7duppy);
        when(tagRepository.getTagByTagName("duplicate")).thenReturn(duplicates);

        List<Tag> result = tagService.getTagByTagName("duplicate");

        assertThat(result).isNotNull();
        assertEquals(duplicates.size(), result.size());
        assertIterableEquals(duplicates, result);
    }

    @Test
    public void gettingNothingWhenGettingTagsByNameThatDoesntExist() {
        List<Tag> emptyList = List.of();
        when(tagRepository.getTagByTagName("nonExistent")).thenReturn(emptyList);

        List<Tag> result = tagService.getTagByTagName("nonExistent");

        assertThat(result).isNotNull();
        assertEquals(0, result.size());
        assertIterableEquals(emptyList, result);

    }

    @Test
    void savingATag() {
        when(tagRepository.save(tag1)).thenReturn(new Tag(1242, tag1.getName()));

        tagService.save(tag1);

        verify(tagRepository, times(1)).save(tag1);
    }
}
