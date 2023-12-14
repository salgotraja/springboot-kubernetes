import {fetchBookmarks} from "@/services/api";
import Bookmarks from "@/components/Bookmarks";
import SearchForm from "@/components/SearchForm";

interface Props {
    searchParams: { [key: string]: string | undefined }
}

const BookmarkPage = async ({ searchParams }: Props) => {
    const pageNo: number = Number(searchParams?.page) || 1
    const query: string = searchParams?.query || ""

    const bookmarks = await fetchBookmarks(pageNo, query);
    return (
        <div>
            <SearchForm/>
            <Bookmarks bookmarks={bookmarks} query={query}/>
        </div>
    );
};

export default BookmarkPage;