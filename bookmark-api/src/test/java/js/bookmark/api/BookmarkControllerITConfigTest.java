package js.bookmark.api;

import js.bookmark.domain.Bookmark;
import js.bookmark.domain.BookmarkRepository;
import js.bookmark.infra.BookmarkApiTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Import(BookmarkApiTestConfig.class)
public class BookmarkControllerITConfigTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp(){
        bookmarkRepository.deleteAll();
        System.out.println("Setting up bookmarks!!!!!");

        List<Bookmark> bookmarks = new ArrayList<>();

        bookmarks.add(new Bookmark(null, "SivaLabs", "https://sivalabs.in", Instant.now()));
        bookmarks.add(new Bookmark(null, "SpringBlog", "https://spring.io/blog", Instant.now()));
        bookmarks.add(new Bookmark(null, "Quarkus", "https://quarkus.io/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Micronaut", "https://micronaut.io/", Instant.now()));
        bookmarks.add(new Bookmark(null, "JOOQ", "https://www.jooq.org/", Instant.now()));
        bookmarks.add(new Bookmark(null, "VladMihalcea", "https://vladmihalcea.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "Thoughts On Java", "https://thorben-janssen.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "DZone", "https://dzone.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "DevOpsBookmarks", "http://www.devopsbookmarks.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Kubernetes docs", "https://kubernetes.io/docs/home/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Expressjs", "https://expressjs.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Marcobehler", "https://www.marcobehler.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "baeldung", "https://www.baeldung.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "devglan", "https://www.devglan.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "linuxize", "https://linuxize.com", Instant.now()));

        bookmarkRepository.saveAll(bookmarks);
    }



    @ParameterizedTest
    @CsvSource({
            "1, 15, 2, 1, true, false, true, false",
            "2, 15, 2, 2, false, true, false, true"
    })
    void shouldGetBookmarks(int pageNo, int totalElements, int totalPages, int currentPage,
                            boolean hasNext, boolean hasPrevious, boolean isFirst, boolean isLast) throws Exception {
        System.out.println("Testing scenarios");
        mvc.perform(get("/api/bookmarks?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
        ;
    }

    @Test
    void shouldCreateBookmarkSuccessfully() throws Exception {
        this.mvc.perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                {
                    "title": "Jagdish Blog",
                    "url": "https://salgotraja.com/"
                }
                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Jagdish Blog")))
                .andExpect(jsonPath("$.url", is("https://salgotraja.com/")));
    }

    @Test
    void shouldFailToCreateBookmarkWhenUrlIsNotPresent() throws Exception{
        this.mvc.perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                {
                    "title": "Jagdish Blog"
                }
                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", is("application/problem+json")))
                .andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
                .andExpect(jsonPath("$.title", is("Constraint Violation")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", is("url")))
                .andExpect(jsonPath("$.violations[0].message", is("Url should not be empty")))
                .andReturn();
    }

    @Test
    void shouldSearchBookmarksByTitle() throws Exception {
        mvc.perform(get("/api/bookmarks?query=spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(true)));
    }

    @Test
    void shouldSearchBookmarksByTitleWithQueryParameter() throws Exception {
        mvc.perform(get("/api/bookmarks?query=spring&page=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(true)));
    }
}
