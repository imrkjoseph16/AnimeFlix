package com.imrkjoseph.animenation.app.util

import com.imrkjoseph.animenation.R

class Default {
    companion object {
        const val ANIME_BASE_URL = "https://anime-nation-server.vercel.app/meta/anilist/"
        const val SERIES_BASE_URL = "https://anime-nation-server.vercel.app/movies/dramacool/"
        const val MOVIES_BASE_URL = "https://anime-nation-server.vercel.app/meta/tmdb/"
        const val MOVIES_VID_ALTERNATIVE_URL = "https://api-movie-source.vercel.app"
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

        const val DEFAULT_MOVIE = "Movie"
        const val DEFAULT_SELECTED_EPISODE = 1
    }

    enum class EntryPointType {
        ALL,
        ANIME,
        KOREAN,
        MOVIES;
    }

    enum class AnimeType(private val screenTitle: Int) {
        TOPANIME(R.string.section_top_10_anime_this_week),
        RECENTANIME(R.string.section_latest_episodes),
        POPULARANIME(R.string.section_popular_anime),
        AIRINGSCHEDULE(R.string.section_anime_airing_schedule),
        RANDOM(R.string.section_random_anime);

        companion object {
            fun getSectionTitle(animeType: AnimeType) = AnimeType.values().find { it == animeType }?.screenTitle
        }
    }
}