package js.bookmark.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT new js.bookmark.api.domain.BookmarkDto(b.id, b.title, b.url, b.createdAt) FROM Bookmark b")
    Page<BookmarkDto> findBookmarks(Pageable pageable);
}