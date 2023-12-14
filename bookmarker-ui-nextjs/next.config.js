/** @type {import('next').NextConfig} */
const nextConfig = {
    serverRuntimeConfig: {
        API_BASE_URL: process.env.SERVER_SIDE_API_BASE_URL
    },
    publicRuntimeConfig: {
        API_BASE_URL: process.env.CLIENT_SIDE_API_BASE_URL
    },
    async redirects() {
        return [
            {
                source: '/',
                destination: '/bookmarks',
                permanent: true,
            },
        ]
    },
}

module.exports = nextConfig
