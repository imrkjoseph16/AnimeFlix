package com.imrkjoseph.animenation.dashboard.shared.data.transformer

import com.imrkjoseph.animenation.app.util.Default.Companion.YEAR_DAY_MONTH_TIME
import com.imrkjoseph.animenation.app.util.Default.Companion.YEAR_MONTH_DAY
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.convertStringDate
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ExploreResultData
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ResultDetails
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.anime.AnimeResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.korean.KoreanResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MovieDetailsResponse
import com.imrkjoseph.animenation.dashboard.shared.data.dto.movies.MoviesResponse
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Character
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Episode
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Name
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.Title
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.VoiceActor
import javax.inject.Inject

class SharedTransformer @Inject constructor() {

    fun transformAnimeDetailsResponse(response: AnimeDetailsResponse) = DetailsFullData(
        description = response.description,
        episodes = response.episodes?.let {
            it.map { episode ->
                Episode(
                    episodeId = episode.id,
                    title = Title(english = episode.title),
                    description = episode.title,
                    number = episode.number,
                    image = episode.image,
                    airDate = episode.airDate,
                    createdAt = episode.createdAt,
                    url = episode.url,
                    type = response.type
                )
            }
        } ?: emptyList(),
        id = response.id,
        image = response.image,
        genres = response.genres,
        otherName = response.otherName,
        synonyms = response.synonyms,
        releaseDate = response.releaseDate.toString(),
        status = response.status,
        subOrDub = response.subOrDub,
        title = response.title?.english ?: response.title?.native,
        totalEpisodes = response.totalEpisodes,
        type = response.type,
        season = response.season,
        characters = response.characters?.let {
              it.map { character ->
                  Character(
                      id = character.id,
                      image = character.image,
                      name = Name(
                          first = character.name?.first,
                          full = character.name?.full,
                          last = character.name?.last,
                          native = character.name?.native
                      ),
                      role = character.role,
                      voiceActors = character.voiceActors?.let { list ->
                          list.map { voiceActor ->
                              VoiceActor(
                                  id = voiceActor.id,
                                  image = voiceActor.image,
                                  language = voiceActor.language,
                                  name = Name(
                                      first = voiceActor.name?.first,
                                      full = voiceActor.name?.full,
                                      last = voiceActor.name?.last,
                                      native = voiceActor.name?.native,
                                  )
                              )
                          }
                      }
                  )
              }
        } ?: emptyList(),
        recommendations = response.recommendations?.map { recommendation ->
            Episode(
                cover = recommendation.cover,
                totalEpisodes = recommendation.episodes,
                episodeId = recommendation.id.toString(),
                image = recommendation.image,
                rating = recommendation.rating,
                status = recommendation.status,
                title = Title(
                    english = recommendation.title?.english,
                    native = recommendation.title?.native
                ),
                type = recommendation.type
            )
        } ?: emptyList(),
        relations = response.relations?.map { relation ->
            Episode(
                color = relation.color,
                cover = relation.cover,
                totalEpisodes = relation.totalEpisodes,
                episodeId = relation.id.toString(),
                image = relation.image,
                rating = relation.rating,
                relationType = relation.relationType,
                status = relation.status,
                title = relation.title?.let { title ->
                    Title(
                        english = title.english,
                        native = title.native
                    )
                },
                type = relation.type
            )
        } ?: emptyList()
    )

    fun transformKoreanDetailsResponse(response: KoreanDetailsResponse) =
        DetailsFullData(
            tags = response.tags,
            casts = response.casts ?: emptyList(),
            production = response.production,
            description = response.description,
            duration = response.duration,
            otherNames = response.otherNames,
            episodes = response.episodes?.let {
                it.mapIndexed { _, episode ->
                    Episode(
                        episodeId = episode.id.orEmpty(),
                        number = episode.number,
                        episode = episode.episode,
                        subType =  episode.subType,
                        url = episode.url,
                        title = Title(
                            english = episode.title
                        ),
                        season = episode.season,
                        releaseDate = convertStringDate(
                            formatDate = YEAR_DAY_MONTH_TIME,
                            inputDate = episode.releaseDate
                        ),
                        image = response.image,
                        type = response.type
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

    fun transformMovieDetailsResponse(response: MovieDetailsResponse) =
        DetailsFullData(
            id = response.showId,
            episodeId = response.episodeId,
            type = response.type,
            casts = response.actors ?: emptyList(),
            description = response.description,
            duration = response.duration.toString(),
            episodes = response.seasons?.get(0)?.episodes?.let {
                it.mapIndexed { _, episode ->
                    Episode(
                        episodeId = episode.id.orEmpty(),
                        showId = response.showId,
                        episode = episode.episode,
                        season = episode.season,
                        url = episode.url,
                        title = Title(english = episode.title),
                        image = episode.img?.hd ?: response.image,
                        releaseDate = convertStringDate(
                            formatDate = YEAR_MONTH_DAY,
                            inputDate = episode.releaseDate
                        )
                    )
                }
            } ?: emptyList(),
            image = response.image,
            genres = response.genres,
            releaseDate = response.releaseDate,
            title = response.title
        )

    fun transformAnimeResponse(animeResponse: AnimeResponse) =
        if (animeResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = animeResponse.currentPage,
            totalResults = animeResponse.totalResults,
            resultDetails = animeResponse.results?.let {
                it.map { result ->
                    ResultDetails(
                        id = result.id,
                        title = result.title?.english ?: result.title?.native,
                        urlLink = result.url,
                        image = result.image,
                        releaseDate = result.releaseDate.toString(),
                        subOrDub = result.episodeNumber
                    )
                }
            } ?: emptyList()
        )

    fun transformKoreanResponse(koreanResponse: KoreanResponse) =
        if (koreanResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = koreanResponse.currentPage,
            hasNextPage = koreanResponse.hasNextPage,
            totalResults = koreanResponse.totalResults,
            resultDetails = koreanResponse.results?.let {
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

    fun transformMoviesResponse(moviesResponse: MoviesResponse) =
        if (moviesResponse.results?.isEmpty() == true) null
        else ExploreResultData(
            currentPage = moviesResponse.currentPage,
            hasNextPage = moviesResponse.hasNextPage,
            totalResults = moviesResponse.totalResults,
            resultDetails = moviesResponse.results?.let {
                it.map { result ->
                    ResultDetails(
                        id = result.id.toString(),
                        title = result.title,
                        releaseDate = result.releaseDate,
                        type = result.type,
                        image = result.image
                    )
                }
            } ?: emptyList()
        )
}