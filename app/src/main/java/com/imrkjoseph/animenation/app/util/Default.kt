package com.imrkjoseph.animenation.app.util

class Default {
    companion object {
        const val ANIME_BASE_URL = "https://anime-server-peach.vercel.app/meta/anilist/"
        const val SERIES_BASE_URL = "https://anime-server-peach.vercel.app/movies/dramacool/"
        const val MOVIES_BASE_URL = "https://anime-server-peach.vercel.app/meta/tmdb/"
        const val VIDS_STREAMING = "vidstreaming"
        const val ASIAN_LOAD = "asianload"
        const val ANIME_DEFAULT_SEARCH = "Korean"
        const val KOREAN_DEFAULT_SEARCH = "Korean"
        const val MOVIE_DEFAULT_SEARCH = "2024"

        const val EMAIL_NOT_VERIFIED_MSG = "Your account is not yet verified, please check your email."
        const val EMAIL_VERIFICATION_MSG = "We have sent an email with a confirmation link to your email address. Please allow 5-10 minutes for this message to arrive."

        const val FIELD_REQUIRED = "This field must be required."

        const val DB_USER = "users"
        const val DB_FAVORITES = "favorites"

        const val YEAR_MONTH_DAY = "yyyy-MM-dd"
        const val YEAR_DAY_MONTH_TIME = "yyyy-MM-dd HH:mm:ss"
        const val MONTH_DAY_YEAR_TIME = "MMM dd,yyyy - h:mm a"

        const val TOTAL_EPISODES = "Total Episodes:"
    }
}
enum class EntryPointType {
    ALL,
    ANIME,
    KOREAN,
    MOVIES;
}