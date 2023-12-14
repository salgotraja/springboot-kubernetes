package js.bookmark.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT new js.bookmark.domain.BookmarkDTO(b.id, b.title, b.url, b.createdAt) FROM Bookmark b")
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    @Query("""
    SELECT new js.bookmark.domain.BookmarkDTO(b.id, b.title, b.url, b.createdAt) FROM Bookmark b
    WHERE lower(b.title) like lower(concat('%', :query, '%'))
    """)
    Page<BookmarkDTO> searchBookmarks(String query, Pageable pageable);
    Page<BookmarkDTO> findByTitleContainsIgnoreCase(String query, Pageable pageable);
    //Page<BookmarkVM> findByTitleContainsIgnoreCase(String query, Pageable pageable);
}