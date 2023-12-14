import {BookmarksResponse} from "@/services/models";
import Bookmark from "@/components/Bookmark";
import Pagination from "@/components/Pagination";

interface BookmarkProps {
    bookmarks: BookmarksResponse
}
const Bookmarks = ({ bookmarks }: BookmarkProps) => {
    return (
        <div>
            <Pagination bookmarks={bookmarks}/>
            {bookmarks.data.map((bookmark) => (
                <Bookmark key={bookmark.id} bookmark={bookmark}/>
            ))}

        </div>

    );
}

export default Bookmarks;