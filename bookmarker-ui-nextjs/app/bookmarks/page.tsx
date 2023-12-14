import {fetchBookmarks} from "@/services/api";
import Bookmarks from "@/components/Bookmarks";

interface Props {
    searchParams: { [key: string]: string | undefined }
}

const BookmarkPage = async ({ searchParams }: Props) => {
    const pageNo: number = Number(searchParams?.page) || 1
    const bookmarks = await fetchBookmarks(pageNo);
    return (
        <div>
            <Bookmarks bookmarks={bookmarks}/>
        </div>
    );
};

export default BookmarkPage;