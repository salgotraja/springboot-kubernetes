import {BookmarksResponse} from "@/services/models";
import Link from "next/link";

interface PaginationProps {
    bookmarks: BookmarksResponse
}

const Pagination = ({ bookmarks }: PaginationProps ) => {
    const path = "/bookmarks";
    const firstPage = {pathname: path, query: {page: 1}}
    const previousPage = {pathname: path, query: {page: bookmarks.currentPage - 1 }}
    const nextPage = {pathname: path, query: {page: bookmarks.currentPage + 1 }}
    const lastPage = {pathname: path, query: {page: bookmarks.totalPages }}
    return (
        <div>
            <nav aria-label="Page navigation">
                <ul className="pagination justify-content-center">

                    <li className={"page-item " + (bookmarks.hasPrevious ? "" : "disabled")}>
                        <Link href={firstPage} className="page-link">First</Link>
                    </li>

                    <li className={"page-item " + (bookmarks.hasPrevious ? "" : "disabled")}>
                        <Link href={previousPage} className="page-link">Previous</Link>
                    </li>

                    <li className={"page-item " + (bookmarks.hasNext ? "" : "disabled")}>
                        <Link href={nextPage} className="page-link">Next</Link>
                    </li>

                    <li className={"page-item " + (bookmarks.hasNext ? "" : "disabled")}>
                        <Link href={lastPage} className="page-link">Last </Link>
                    </li>
                </ul>
            </nav>

        </div>
    );
}

export default Pagination;