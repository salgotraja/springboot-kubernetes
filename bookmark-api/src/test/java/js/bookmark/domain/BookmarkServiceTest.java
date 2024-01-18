package js.bookmark.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private BookmarkMapper bookmarkMapper;

    @InjectMocks
    private BookmarkService bookmarkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetBookmarks() {
        Pageable pageable = getPageable(1);

        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(bookmarkRepository.findBookmarks(pageable)).thenReturn(bookmarkPage);

        // Call the service method
        BookmarksDTO result = bookmarkService.getBookmarks(1);

        assertNotNull(result);
        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getData().size()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
        assertThat(result.isHasNext()).isFalse();
        assertThat(result.isHasPrevious()).isFalse();

        verify(bookmarkRepository).findBookmarks(pageable);
    }

    @Test
    public void testSearchBookmarks() {
        String query = "java";
        Pageable pageable = getPageable(1);

        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(bookmarkRepository.findByTitleContainsIgnoreCase(query, getPageable(1))).thenReturn(bookmarkPage);


        BookmarksDTO result = bookmarkService.searchBookmarks(query, 1);

        assertNotNull(result);
        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getData().size()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();

        verify(bookmarkRepository).findByTitleContainsIgnoreCase(query, getPageable(1));
    }

    @Test
    public void testSearchBookmarks_WithQueryAndPage() {
        String query = "java";
        Pageable pageable = getPageable(1);

        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(bookmarkRepository.findByTitleContainsIgnoreCase(query, getPageable(1))).thenReturn(bookmarkPage);

        BookmarksDTO result = bookmarkService.searchBookmarks(query, 1);

        assertNotNull(result);
        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getData().size()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();

        verify(bookmarkRepository).findByTitleContainsIgnoreCase(query, getPageable(1));
    }

    @Test
    public void testCreateBookmark() {
        Instant fixedInstant = Instant.parse(Instant.now().toString());

        CreateBookmarkRequest request = new CreateBookmarkRequest("Test Title", "https://www.test.com");
        Bookmark bookmark = new Bookmark(1L, "Test Title", "https://www.test.com", fixedInstant);
        BookmarkDTO bookmarkDTO = new BookmarkDTO(1L, "Test Title", "https://www.test.com", fixedInstant);

        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);
        when(bookmarkMapper.toDTO(bookmark)).thenReturn(bookmarkDTO);

        BookmarkDTO result = bookmarkService.createBookmark(request);

        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Title");
        assertThat(result.getUrl()).isEqualTo("https://www.test.com");
        assertThat(result.getCreatedAt()).isEqualTo(fixedInstant);

        verify(bookmarkRepository).save(any(Bookmark.class));
        verify(bookmarkMapper).toDTO(bookmark);
    }



    private Pageable getPageable(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        return PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
    }

}