import {BookmarksResponse} from "@/services/models";

console.log("process.env.NEXT_PUBLIC_API_BASE_URL: ", process.env.NEXT_PUBLIC_API_BASE_URL)
export const fetchBookmarks = async (page: number, query: string): Promise<BookmarksResponse> => {
    const apiUrl = process.env.SERVER_SIDE_API_BASE_URL;
    console.log("API Url fetchBookmarks: ", apiUrl)
    let url = `${apiUrl}/api/bookmarks?page=${page}`
    if (query) {
        url += `&query=${query}`
    }
    const response = await fetch(url,{
        method: 'GET',
        headers: {
            'Cache-Control': 'no-cache',
        },
        cache: 'no-cache',
    });
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return await response.json();
}

export const saveBookmark = async (bookmark: { title: string; url: string; }) => {
    const apiUrl = process.env.NEXT_PUBLIC_API_BASE_URL;
    console.log("public api url: ", apiUrl)
    try {
        const response = await fetch(`${apiUrl}/api/bookmarks`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookmark),
        });

        if (!response.ok) {
            const errorData = await response.json();
            const DEFAULT_ERROR_MSG = 'Network response was not ok';
            throw new Error(errorData.message || DEFAULT_ERROR_MSG);
        }

        const data = await response.json()

        return Response.json(data)
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
};
