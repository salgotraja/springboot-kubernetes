package js.bookmark.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import js.bookmark.domain.BookmarkDTO;
import js.bookmark.domain.BookmarkService;
import js.bookmark.domain.BookmarksDTO;
import js.bookmark.domain.CreateBookmarkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookmarkController.class)
class BookmarkControllerTest {

    @MockBean
    private BookmarkService bookmarkService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBookmarksTest() throws Exception {
        BookmarkDTO bookmark = new BookmarkDTO(1L, "title", "url", Instant.now());
        List<BookmarkDTO> bookmarkList = Collections.singletonList(bookmark);

        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(bookmarkList);

        BookmarksDTO bookmarksDTO = new BookmarksDTO(bookmarkPage);

        given(bookmarkService.getBookmarks(1)).willReturn(bookmarksDTO);

        mockMvc.perform(get("/api/bookmarks?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].title", is("title")))
                .andReturn();
    }

    @Test
    public void searchBookmarksTest() throws Exception {
        BookmarkDTO bookmark = new BookmarkDTO(1L, "title", "url", Instant.now());
        List<BookmarkDTO> bookmarkList = Collections.singletonList(bookmark);

        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(bookmarkList);

        BookmarksDTO bookmarksDTO = new BookmarksDTO(bookmarkPage);

        given(bookmarkService.searchBookmarks("query", 1)).willReturn(bookmarksDTO);

        mockMvc.perform(get("/api/bookmarks?query=query&page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].title", is("title")))
                .andReturn();
    }

    @Test
    public void createBookmarkTest() throws Exception {
        CreateBookmarkRequest request = new CreateBookmarkRequest("Jagdish Blog", "https://salgotraja.com/");
        BookmarkDTO bookmark = new BookmarkDTO(1L, "Jagdish Blog", "https://salgotraja.com/", Instant.now());

        given(bookmarkService.createBookmark(any(CreateBookmarkRequest.class))).willReturn(bookmark);

        // Convert request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the test
        mockMvc.perform(post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Jagdish Blog")))
                .andExpect(jsonPath("$.url", is("https://salgotraja.com/")));
    }

    @Test
    void shouldFailToCreateBookmarkWhenUrlIsNotPresent() throws Exception {
        mockMvc.perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
            {
                "title": "Jagdish Blog"
            }
            """))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void getBookmarksTest_shouldReturnEmptyList_whenNoBookmarksFound() throws Exception {
        List<BookmarkDTO> bookmarkList = Collections.emptyList();
        Page<BookmarkDTO> bookmarkPage = new PageImpl<>(bookmarkList);
        BookmarksDTO bookmarksDTO = new BookmarksDTO(bookmarkPage);

        given(bookmarkService.getBookmarks(1)).willReturn(bookmarksDTO);

        mockMvc.perform(get("/api/bookmarks?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }
}