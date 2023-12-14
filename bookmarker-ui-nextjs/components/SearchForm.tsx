"use client";
import {SyntheticEvent, useState} from "react";
import {useRouter} from "next/navigation";

const SearchForm = () => {
    const router = useRouter();
    const [query, setQuery] = useState("");

    const handleSearch = async (e: SyntheticEvent) => {
        e.preventDefault();
        if (query === "") {
            router.push(`/bookmarks`)
            return;
        }

        router.push(`/bookmarks?page=1&query=${query}`);
    }

    return (
        <div className={"pb-3"}>
            <form method="get" onSubmit={handleSearch}>
                <div className="row g-3 align-items-center">
                    <div className="col">
                        <input className="form-control"
                               type="search" name="query"
                               value={query}
                               onChange={(e) => setQuery(e.target.value)}/>
                    </div>
                    <div className="col-auto">
                        <button type="submit" className="btn btn-primary">Search</button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default SearchForm;