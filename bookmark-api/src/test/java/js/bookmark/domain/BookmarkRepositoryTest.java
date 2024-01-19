package js.bookmark.domain;

import jakarta.persistence.EntityManager;
import js.bookmark.infra.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Import(TestContainerConfiguration.class)
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    @Transactional
    void setUp() {
        bookmarkRepository.deleteAll();

        entityManager.persist(new Bookmark(null, "Title 1", "https://www.example.com/1", Instant.now()));
        entityManager.persist(new Bookmark(null, "Title 2", "https://www.example.com/2", Instant.now()));

        entityManager.flush();
    }

    @Test
    void testFindBookmarks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookmarkDTO> bookmarks = bookmarkRepository.findBookmarks(pageable);

        assertThat(bookmarks).isNotNull();
        assertThat(2).isEqualTo(bookmarks.getTotalElements());
    }

    @Test
    void testSearchBookmark() {
        String searchTerm = "Title 1";
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookmarkDTO> bookmarks = bookmarkRepository.searchBookmarks(searchTerm, pageable);

        assertThat(bookmarks).isNotNull();
        assertThat(1).isEqualTo(bookmarks.getTotalElements());
        assertThat("Title 1").isEqualTo(bookmarks.getContent().getFirst().getTitle());
    }

    @Test
    void testFindByTitleContainsIgnoreCase() {
        String searchTerm = "title";
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookmarkDTO> bookmarks = bookmarkRepository.findByTitleContainsIgnoreCase(searchTerm, pageable);

        assertThat(bookmarks).isNotNull();
        assertThat(2).isEqualTo(bookmarks.getTotalElements());
    }
}