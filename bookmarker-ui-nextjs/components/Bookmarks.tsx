import {BookmarksResponse} from "@/services/models";
import Bookmark from "@/components/Bookmark";
import Pagination from "@/components/Pagination";

interface BookmarkProps {
    bookmarks: BookmarksResponse
    query?: string
}
const Bookmarks = ({ bookmarks, query }: BookmarkProps) => {
    return (
        <div>
            <Pagination bookmarks={bookmarks} query={query}/>
            {bookmarks.data.map((bookmark) => (
                <Bookmark key={bookmark.id} bookmark={bookmark}/>
            ))}

        </div>

    );
}

export default Bookmarks;