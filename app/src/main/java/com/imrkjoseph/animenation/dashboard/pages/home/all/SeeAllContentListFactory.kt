package com.imrkjoseph.animenation.dashboard.pages.home.all

import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsLargeItem
import com.imrkjoseph.animenation.app.shared.dto.CardDetailsLargeItemViewDto
import com.imrkjoseph.animenation.app.util.Default.EntryPointType
import com.imrkjoseph.animenation.app.util.ViewUtil.Companion.getRandomRatings
import com.imrkjoseph.animenation.dashboard.pages.home.list.GetAnimeUiItems
import javax.inject.Inject

class SeeAllContentListFactory @Inject constructor(){

    fun createOverview(animeUiResult: GetAnimeUiItems) = animeUiResult.prepareList()

    private fun GetAnimeUiItems.prepareList() = shouldGetListResults()?.map {
        CardDetailsLargeItem(
            dto = CardDetailsLargeItemViewDto(
                itemId = it.id,
                title = it.title?.english ?: it.title?.native,
                itemImageUrl = it.image,
                itemStatus = it.episodeTitle,
                itemDescription = it.description ?: it.type,
                itemType = it.type,
                itemEntryPoint = EntryPointType.ANIME,
                rating = it.rating ?: getRandomRatings()
            )
        )
    } ?: emptyList()

    private fun GetAnimeUiItems.shouldGetListResults() = when {
        topAnimeList != null -> topAnimeList.results
        recentEpisodesList != null -> recentEpisodesList.results
        popularAnimeList != null -> popularAnimeList.results
        airingScheduleList != null -> airingScheduleList.results
        else -> randomAnimeList?.results
    }
}