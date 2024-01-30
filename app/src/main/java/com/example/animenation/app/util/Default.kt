package com.example.animenation.app.util

class Default {
    companion object {
        const val ANIME_BASE_URL = "https://anime-server-peach.vercel.app/anime/gogoanime/"
        const val MOVIES_BASE_URL = "https://anime-server-peach.vercel.app/movies/dramacool/"
        const val VIDS_STREAMING = "vidstreaming"
        const val ASIAN_LOAD = "asianload"
        const val EXPLORE_DEFAULT_SEARCH = "Korean"
    }
}
enum class EntryPointType {
    ANIME,
    SERIES;
}