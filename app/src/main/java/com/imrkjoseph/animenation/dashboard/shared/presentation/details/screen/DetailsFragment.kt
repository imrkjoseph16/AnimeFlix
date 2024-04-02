package com.imrkjoseph.animenation.dashboard.shared.presentation.details.screen

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.imrkjoseph.animenation.NavGraphDetailsDirections
import com.imrkjoseph.animenation.R
import com.imrkjoseph.animenation.app.foundation.BaseFragment
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsLargeItem
import com.imrkjoseph.animenation.app.shared.binder.component.LoadingItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.SectionTitleItem
import com.imrkjoseph.animenation.app.shared.binder.component.setupCardDetailsLargeItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupHorizontalListItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupOtherEpisodesItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupOtherRelatedItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupSectionTitleItemBinder
import com.imrkjoseph.animenation.app.util.Default.Companion.DEFAULT_SELECTED_EPISODE
import com.imrkjoseph.animenation.app.util.EntryPointType
import com.imrkjoseph.animenation.app.util.showFancyToast
import com.imrkjoseph.animenation.dashboard.shared.data.dto.favorites.FavoritesDetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.GetContentDetails
import com.imrkjoseph.animenation.dashboard.shared.presentation.SharedViewModel
import com.imrkjoseph.animenation.dashboard.shared.presentation.ShowDetailsError
import com.imrkjoseph.animenation.dashboard.shared.presentation.ShowFavorites
import com.imrkjoseph.animenation.dashboard.shared.presentation.details.data.DetailsFullData
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation.VideoStreamingArguments
import com.imrkjoseph.animenation.databinding.FragmentDetailsBinding
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(bindingInflater = FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by viewModels()

    private val navArgs: DetailsFragmentArgs by navArgs()

    private var selectedType = DetailsOtherSelection.EPISODES

    override fun onViewCreated() {
        super.onViewCreated()
        onBackPressedCallBack(::goBackToPreviousScreen)
        with(binding) {
            configureViews()
            setupObserver()
        }
    }

    private fun FragmentDetailsBinding.configureViews() {
        back.setOnClickListener {
            goBackToPreviousScreen()
        }

        watchContent.setOnClickListener {
            goToStreamVideoScreen(
                episodeId = getPairDetailsId().first,
                showId = getPairDetailsId().second,
                selectedEpisode = DEFAULT_SELECTED_EPISODE
            )
        }

        addFavorites.setOnClickListener {
            details?.let { data ->
                sharedViewModel.addToFavorites(
                    details = FavoritesDetailsFullData(
                        favoriteId = data.id,
                        favoriteName = data.title,
                        favoritesImage = data.image,
                        releaseDate = data.releaseDate,
                        favoritesType = navArgs.argument.entryPointType.name
                    )
                )
            }
        }

        selectionTab.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                selectedType = when(tab.position) {
                    0 -> DetailsOtherSelection.EPISODES
                    1 -> DetailsOtherSelection.RELATED
                    else -> DetailsOtherSelection.RECOMMENDED
                }

                details?.let {
                    viewModel.getUiSelectionItems(
                        result = it,
                        selectedType = selectedType,
                        navArgs = navArgs.argument
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        // List of cast
        setupCastList()
        // List of episodes, related and recommendations.
        setupSelectionList()
    }

    private fun FragmentDetailsBinding.getPairDetailsId(): Pair<String?, String?> {
        val episodeId = if (details?.episodes?.isNotEmpty() == true)
            details?.episodes?.get(0)?.episodeId
        else details?.episodeId

        val showId = if (details?.episodes?.isNotEmpty() == true)
            details?.episodes?.get(0)?.showId
        else details?.id

        return Pair(first = episodeId, second = showId)
    }

    private fun FragmentDetailsBinding.setupCastList() {
        with(castList) {
            addItemBindings(viewHolders = setupSectionTitleItemBinder(dtoRetriever = SectionTitleItem::dto))
            addItemBindings(viewHolders = setupHorizontalListItemBinder {})
        }
    }

    private fun FragmentDetailsBinding.setupSelectionList() {
        with(otherSelectionList) {
            addScrollObservable()
            addItemBindings(viewHolders = LoadingItemBinder)
            addItemBindings(viewHolders = setupOtherEpisodesItemBinder(
                onItemClick = { episode ->
                    goToStreamVideoScreen(
                        episodeId = episode.itemEpisodeId,
                        showId = episode.itemShowId,
                        selectedEpisode = episode.currentEpisode ?: DEFAULT_SELECTED_EPISODE
                    )
                }
            ))
            addItemBindings(viewHolders = setupOtherRelatedItemBinder(onItemClick = {
                refreshAndGetNewDetails(
                    args = DetailsArguments(
                        detailsId = it.itemId,
                        entryPointType = it.itemEntryPointType,
                        typeOfMovie = it.itemType
                    )
                )
            }))
            addItemBindings(viewHolders = setupCardDetailsLargeItemBinder(
                dtoRetriever = CardDetailsLargeItem::dto,
                onItemClick = {
                    refreshAndGetNewDetails(
                        args = DetailsArguments(
                            detailsId = it.dto.itemId,
                            entryPointType = it.dto.itemEntryPoint,
                            typeOfMovie = it.dto.itemType
                        )
                    )
                }
            ))
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(sharedViewModel) {
                launch {
                    sharedState.collectLatest { newState ->
                        when(newState) {
                            is GetContentDetails -> newState.updateUi()
                            is ShowDetailsError -> showFancyToastError(newState.throwable.message.toString())
                            is ShowFavorites -> {
                                if (newState.isSuccess) showFancyToast(message = getString(R.string.title_favorites_success))
                                else showFancyToastError(getString(R.string.title_favorites_failed))
                            }
                        }
                    }
                }
            }

            with(viewModel) {
                launch {
                    uiState.collectLatest { uiModel ->
                        uiModel.updateUiDetailsList()
                    }
                }
            }
        }
        sharedViewModel.verifyDetailsState(
            navArgs.argument.entryPointType,
            navArgs.argument.detailsId,
            navArgs.argument.typeOfMovie
        )
    }

    private fun RecyclerView.addScrollObservable() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN).not()
                    && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.exploreAdditionalEpisodes(selectedType = selectedType, navArgs = navArgs.argument)
                }
            }
        })
    }

    private fun DetailsUiModel.updateUiDetailsList() {
        binding.apply {
            castList.setItems(items = castItems)
            otherSelectionList.setItems(items = otherSelectionItems)
        }
    }

    private fun showFancyToastError(message: String) {
        showFancyToast(
            message = message,
            style = FancyToast.ERROR,
            duration = FancyToast.LENGTH_LONG
        )
    }

    private fun goBackToPreviousScreen() = requireActivity().finish()

    private fun goToStreamVideoScreen(
        episodeId: String?,
        showId: String?,
        selectedEpisode: Int
    ) {
        episodeId?.let { id ->
            findNavController().navigate(
                directions = NavGraphDetailsDirections.actionToAnimeVideoScreen(
                    argument = VideoStreamingArguments(
                        detailsId = navArgs.argument.detailsId,
                        typeOfMovie = navArgs.argument.typeOfMovie,
                        episodeId = id,
                        showId = showId,
                        entryPointType = navArgs.argument.entryPointType,
                        selectedEpisode = selectedEpisode
                    )
                )
            )
        } ?: error("episodeId not found")
    }

    private fun refreshAndGetNewDetails(args: DetailsArguments) {
        findNavController().navigate(
            directions = DetailsFragmentDirections.actionToDetailsScreen(
            argument = args
        ))
    }

    private fun GetContentDetails.updateUi() {
        binding.apply {
            details = detailsData
            details?.let {
                canShowCasts = it.verifyCanShowCasts()
                viewModel.getCastItems(cast = it.characters, actors = it.casts)
                viewModel.getUiSelectionItems(
                    result = it,
                    selectedType = selectedType,
                    navArgs = navArgs.argument
                )
            }
            executePendingBindings()
        }
    }

    private fun DetailsFullData.verifyCanShowCasts() = with(navArgs.argument) {
        when {
            // Check the characters if it's not null,
            // this was another set of cast list since,
            // the Anime response is different data from Korean and Movies.
            characters.isNotEmpty() &&
            entryPointType == EntryPointType.ANIME -> true
            // Check "casts" list if it's not null,
            // it means the entryType is either Korean or Movies,
            casts.isNotEmpty() -> true
            else -> false
        }
    }

    override fun onDestroyView() {
        resetViews()
        super.onDestroyView()
    }

    private fun resetViews() {
        with(binding) {
            castList.adapter = null
            otherSelectionList.adapter = null
        }
    }
}