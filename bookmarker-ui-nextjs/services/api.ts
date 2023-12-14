import {BookmarksResponse} from "@/services/models";
const API_BASE_URL = "http://localhost:8080"

/*const getApiUrl = () => {
    return serverRuntimeConfig.API_BASE_URL || publicRuntimeConfig.API_BASE_URL;
}

console.log(getApiUrl())*/

export const fetchBookmarks = async (page: number): Promise<BookmarksResponse> => {
    const response = await fetch(`${API_BASE_URL}/api/bookmarks?page=${page}`, {
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

    try {
        const response = await fetch(`${API_BASE_URL}/api/bookmarks`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookmark),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Network response was not ok');
        }

        const data = await response.json()

        return Response.json(data)
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
};
