package js.bookmark.domain;

import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {
    public BookmarkDto toDTO(Bookmark bookmark) {
        return new BookmarkDto(
                bookmark.getId(),
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getCreatedAt()
        );
    }
}