package com.imrkjoseph.animenation.dashboard.pages.home.search

import com.imrkjoseph.animenation.app.component.TextLine
import com.imrkjoseph.animenation.app.shared.binder.data.SearchAnimeItem
import com.imrkjoseph.animenation.app.shared.dto.SearchAnimeItemViewDto
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ExploreResultData
import javax.inject.Inject

class SearchFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(searchResult: ExploreResultData) = setupSearchAnimeDetails(searchResult)

    private fun setupSearchAnimeDetails(results: ExploreResultData) =
        results.resultDetails?.mapIndexed { _, result ->
            SearchAnimeItem(
                dto = SearchAnimeItemViewDto(
                    itemId = result.id,
                    itemTitle = TextLine(text = result.title),
                    itemReleasedDate = TextLine(text = result.releaseDate),
                    itemTag = TextLine(text = result.subOrDub),
                    itemUrl = result.urlLink,
                    itemImageUrl = result.image,
                )
            )
        }
}