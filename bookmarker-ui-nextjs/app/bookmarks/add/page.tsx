import React from "react";
import {AddBookmark} from "@/components/AddBookmark";

const Page = () => {

    return (
        <div>
            <div className="card">
                <div className="card-header text-center">
                    <h2>Create New Bookmark</h2>
                </div>
                <div className="card-body">
                    <AddBookmark />
                </div>
            </div>
        </div>
    );

}

export default Page;