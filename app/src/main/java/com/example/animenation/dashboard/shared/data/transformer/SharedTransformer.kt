package com.example.animenation.dashboard.shared.data.transformer

import com.example.animenation.dashboard.pages.explore.data.ExploreResultData
import com.example.animenation.dashboard.pages.explore.data.ResultDetails
import com.example.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.example.animenation.dashboard.shared.presentation.details.data.Episode
import com.example.animenation.dashboard.shared.data.ISearchAvailableAnime
import com.example.animenation.dashboard.shared.data.ISearchAvailableMovies
import com.example.animenation.dashboard.shared.data.ISearchExploreSeries
import com.example.animenation.dashboard.shared.data.dto.anime.AnimeDetailsResponse
import com.example.animenation.dashboard.shared.data.dto.movies.SeriesDetailsResponse
import javax.inject.Inject

class SharedTransformer @Inject constructor() {

    fun transformAnimeDetailsResponse(response: AnimeDetailsResponse) = DetailsFullData(
        description = response.description,
        episodes = response.episodes?.let {
            it.mapIndexed { _, episode ->
                Episode(
                    id = episode.id,
                    number = episode.number,
                    url = episode.url
                )
            }
        } ?: emptyList(),
        id = response.id,
        image = response.image,
        genres = response.genres,
        otherName = response.otherName,
        releaseDate = response.releaseDate,
        status = response.status,
        subOrDub = response.subOrDub,
        title = response.title,
        totalEpisodes = response.totalEpisodes,
        type = response.type,
        url = response.url
    )

    fun transformSeriesDetailsResponse(response: SeriesDetailsResponse) =
        DetailsFullData(
            tags = response.tags,
            casts = response.casts,
            production = response.production,
            description = response.description,
            duration = response.duration,
            otherNames = response.otherNames,
            episodes = response.episodes?.let {
                it.mapIndexed { _, episode ->
                    Episode(
                        id = episode.id,
                        number = episode.number,
                        episode = episode.episode,
                        subType =  episode.subType,
                        url = episode.url,
                        title = episode.title,
                        season = episode.season,
                        releaseDate = episode.releaseDate
                    )
                }
            } ?: emptyList(),
            id = response.id,
            image = response.image,
            genres = response.genres,
            releaseDate = response.releaseDate,
            title = response.title,
            type = response.type,
            url = response.url
        )

    fun transformAnimeResponse(animeResponse: ISearchAvailableAnime) =
        if (animeResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = animeResponse.currentPage,
            hasNextPage = animeResponse.hasNextPage,
            resultDetails = animeResponse.results?.let {
                it.map { result ->
                    ResultDetails(
                        id = result.id,
                        title = result.title,
                        urlLink = result.url.orEmpty(),
                        image = result.image.orEmpty(),
                        releaseDate = result.releaseDate,
                        subOrDub = result.subOrDub
                    )
                }
            } ?: emptyList()
        )

    fun transformMoviesResponse(moviesResponse: ISearchAvailableMovies) =
        if (moviesResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = moviesResponse.currentPage,
            hasNextPage = moviesResponse.hasNextPage,
            resultDetails = moviesResponse.results?.let {
                it.map { result ->
                    ResultDetails(
                        id = result.id,
                        title = result.title,
                        urlLink = result.url,
                        releaseDate = result.releaseDate,
                        type = result.type,
                        image = result.image
                    )
                }
            } ?: emptyList()
        )

    fun transformExploreResponse(exploreResponse: ISearchExploreSeries) =
        if (exploreResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = exploreResponse.currentPage,
            hasNextPage = exploreResponse.hasNextPage,
            resultDetails = exploreResponse.results?.let {
                it.map { result ->
                    ResultDetails(
                        id = result.id,
                        title = result.title,
                        urlLink = result.url,
                        releaseDate = result.releaseDate,
                        type = result.type,
                        image = result.image
                    )
                }
            } ?: emptyList()
        )
}