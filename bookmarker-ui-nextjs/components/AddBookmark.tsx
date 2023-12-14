"use client"
import React, {useState} from "react";
import {saveBookmark} from "@/services/api";

interface MessageInfo {
    message: string | null;
    isSuccess: boolean;
}

export const AddBookmark = () => {

    const [title, setTitle] = useState("");
    const [url, setUrl] = useState("");
    const [messageInfo, setMessageInfo] = useState<MessageInfo>({ message: null, isSuccess: true });

    const onFocusHandler = () => {
        setMessageInfo({ message: null, isSuccess: true });
    }

    const handleSubmit = async (e: React.SyntheticEvent) => {
        e.preventDefault();
        if(!url) {
            setMessageInfo({ message: "URL is required", isSuccess: false });
            return;
        }
        const payload = {
            title,
            url
        }

        try {
            const response = await saveBookmark(payload);
            console.log("SaveBookmark response: ", response);
            setTitle("");
            setUrl("");
            setMessageInfo({ message: "Bookmark saved successfully", isSuccess: true });

            setTimeout(() => {
                setMessageInfo({ message: null, isSuccess: true });
            }, 3000);
        } catch (error) {
            console.error("Error saving bookmark: ", error);
            setMessageInfo({ message: "Failed to save bookmark", isSuccess: false });

            setTimeout(() => {
                setMessageInfo({ message: null, isSuccess: true });
            }, 3000);
        }
    }

    return (
        <div className="card-text">
            {messageInfo.message && (
                <div className={`alert ${messageInfo.isSuccess ? 'alert-success' : 'alert-danger'}`} role="alert">
                    {messageInfo.message}
                </div>
            )}
            <form onSubmit={e => handleSubmit(e)}>
                <div className="mb-3">
                    <label htmlFor="url" className="form-label">URL</label>
                    <input type="text" className="form-control" id="url"
                           value={url} onChange={e => setUrl(e.target.value)} onFocus={onFocusHandler}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">Title</label>
                    <input type="text" className="form-control" id="title"
                           value={title} onChange={e => setTitle(e.target.value)} />
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );
}