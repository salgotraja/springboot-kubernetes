package js.bookmark.infra;

import js.bookmark.BookmarkApiApplication;
import org.springframework.boot.SpringApplication;

public class TestBookmarkApiApplication {
    public static void main(String[] args) {
        SpringApplication.from(BookmarkApiApplication::main).with(BookmarkApiTestConfig.class).run(args);
    }

}
