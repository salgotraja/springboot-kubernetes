package js.bookmark.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    private Pageable getPageable(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        return PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
    }

    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page) {
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findBookmarks(getPageable(page));
        return new BookmarksDTO(bookmarkPage);
    }

    @Transactional(readOnly = true)
    public BookmarksDTO searchBookmarks(String query, Integer page) {
        //Page<BookmarkDto> bookmarkPage = bookmarkRepository.searchBookmarks(query, pageable);
        //Page<BookmarkVM> bookmarkVMPage = bookmarkRepository.findByTitleContainsIgnoreCase(query, pageable);
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findByTitleContainsIgnoreCase(query, getPageable(page));
        return new BookmarksDTO(bookmarkPage);
    }

    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        return bookmarkMapper.toDTO(savedBookmark);
    }
}