package com.imrkjoseph.animenation.dashboard.pages.explore.presentation

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.component.TextLine
import com.imrkjoseph.animenation.app.shared.binder.component.ContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.ExploreContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.HorizontalListContentItem
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.binder.component.VerticalListContentItem
import com.imrkjoseph.animenation.app.shared.dto.ContentItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.ExploreContentItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.SectionTitleItemViewDto
import com.imrkjoseph.animenation.app.shared.dto.SpaceItemViewDto
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.dashboard.pages.explore.data.ExploreResultData
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.ExploreType.*
import com.imrkjoseph.animenation.dashboard.pages.explore.presentation.ExploreFactory.HorizontalSectionDetails.*
import javax.inject.Inject

@SuppressLint("SupportAnnotationUsage")
class ExploreFactory @Inject constructor() {

    /**
     * (createOverview) building the layout components.
     * */
    fun createOverview(selectedCategoryType: ExploreType, exploreItems: ExploreUiItems) = exploreItems.prepareList(categoryType = selectedCategoryType)

    private fun ExploreUiItems.prepareList(categoryType: ExploreType) =
        // These 3 sections is for the anime, korean and movies results,
        // when the user is typing in the search field.

        // Anime Result
        animeResult.createCarouselSection(sectionDetails = ANIME_DETAILS) +

        // Korean Result
        koreanResult.createCarouselSection(sectionDetails = KOREAN_DETAILS) +

        // Movies Result
        moviesResult.createCarouselSection(sectionDetails = MOVIES_DETAILS) +

        /** Pre-loaded Explore Items **/
        setupSectionTitle(sectionId = EXPLORE, leftTitle = getExploreSectionTitleLabel(type = categoryType)) +
        exploreResult.setupExploreItem(categoryType = categoryType)

    private fun ExploreResultData?.createCarouselSection(
        sectionDetails: HorizontalSectionDetails,
    ) = buildList {
        if (resultIsNotEmpty()) {
            add(element = setupSectionTitle(sectionDetails.exploreSection, sectionDetails.titleRes))
            add(element = setupCarouselItem(exploreType = sectionDetails.exploreSection))
        } else {
            add(element = SpaceItemViewDto())
        }
    }

    private fun ExploreResultData?.resultIsNotEmpty() = this != null

    private fun setupSectionTitle(
        sectionId: ExploreType,
        @StringRes leftTitle: Int?
    ) = SectionTitleItem(
        id = sectionId,
        dto = SectionTitleItemViewDto(
            leftTitle = TextLine(textRes = leftTitle)
        )
    )

    private fun ExploreResultData?.setupCarouselItem(
        exploreType: ExploreType
    ) = this?.resultDetails?.let {
        HorizontalListContentItem(
            id = exploreType,
            payload = it.map { result ->
                ContentItem(
                    dto =  ContentItemViewDto(
                        itemId = result.id,
                        itemTitle = TextLine(text = result.title),
                        itemImageUrl = result.image,
                        typeOfMovie = result.type,
                        entryPointType = findEntryType(exploreType = exploreType)
                    )
                )
            }
        )
    } ?: SpaceItemViewDto()

    private fun ExploreResultData?.setupExploreItem(
        categoryType: ExploreType
    ) = VerticalListContentItem(
        id = EXPLORE,
        payload = this?.resultDetails?.map { details ->
            ExploreContentItem(
                dto = ExploreContentItemViewDto(
                    itemId = details.id,
                    itemTitle = TextLine(text = details.title),
                    itemImageUrl = details.image,
                    entryPointType = findEntryType(exploreType = categoryType),
                    typeOfMovie = details.type
                )
            )
        } ?: emptyList()
    )

    @StringRes
    private fun getExploreSectionTitleLabel(
        type: ExploreType
    ) = when(type) {
        ANIME -> R.string.title_explore_anime_result
        KOREAN -> R.string.title_explore_korean_result
        else -> R.string.title_explore_movie_result
    }

    enum class ExploreType {
        ANIME,
        KOREAN,
        MOVIES,
        EXPLORE
    }

    enum class HorizontalSectionDetails(
        val exploreSection: ExploreType,
        @StringRes val titleRes: Int
    ) {
        ANIME_DETAILS(exploreSection = ANIME, titleRes = R.string.title_anime_result),
        KOREAN_DETAILS(exploreSection = KOREAN, titleRes = R.string.title_korean_result),
        MOVIES_DETAILS(exploreSection = MOVIES, titleRes = R.string.title_movie_result)
    }

    companion object {
        fun findEntryType(exploreType: ExploreType) = EntryPointType.entries.find { it.name == exploreType.name } ?: EntryPointType.ANIME

        fun findItemViewPosition(type: ExploreType, list: List<Any>) : Int? {
            list.forEachIndexed { index, listItem ->
                if (listItem is SectionTitleItem &&
                    listItem.id == type)
                    return index
            }
            return null
        }
    }
}