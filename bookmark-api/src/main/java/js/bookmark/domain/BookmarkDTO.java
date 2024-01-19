package js.bookmark.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class BookmarkDTO {
    Long id;
    String title;
    String url;
    Instant createdAt;
}