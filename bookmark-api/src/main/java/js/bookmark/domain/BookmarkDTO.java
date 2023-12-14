package js.bookmark.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
public class BookmarkDTO {
    Long id;
    String title;
    String url;
    Instant createdAt;
}