package com.example.animenation.dashboard.shared.presentation.details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.animenation.app.component.CustomRecyclerView
import com.example.animenation.app.component.ListItemPayloadDiffCallback
import com.example.animenation.app.foundation.BaseFragment
import com.example.animenation.app.shared.binder.component.LoadingItemBinder
import com.example.animenation.app.shared.binder.component.SpaceItemViewDtoBinder
import com.example.animenation.app.shared.binder.component.getOtherEpisodeItemBinder
import com.example.animenation.app.shared.binder.data.OtherEpisodeItem
import com.example.animenation.dashboard.pages.home.list.GetAnimeDetails
import com.example.animenation.dashboard.shared.presentation.video.presentation.VideoStreamingArguments
import com.example.animenation.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(bindingInflater = FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun onViewCreated() {
        super.onViewCreated()
        with(binding) {
            configureViews()
            setupOtherEpisodeList()
            setupObserver()
        }
    }

    private fun FragmentDetailsBinding.configureViews() {
        back.setOnClickListener {
            goBackToPreviousScreen()
        }

        watchAnime.setOnClickListener {
            goToStreamVideoScreen(episodeId = binding.details?.episodes?.get(0)?.id)
        }

        onBackPressedCallBack(onBackClicked = ::goBackToPreviousScreen)
    }

    private fun FragmentDetailsBinding.setupOtherEpisodeList() {
        otherEpisodeList.setPresetConfig()
        executePendingBindings()
    }

    private fun CustomRecyclerView.setPresetConfig() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        addScrollObservable()
        addItemBindings(viewHolders = SpaceItemViewDtoBinder)
        addItemBindings(viewHolders = LoadingItemBinder)
        addItemBindings(viewHolders = getOtherEpisodeItemBinder(
            dtoRetriever = OtherEpisodeItem::dto,
            onItemClick = { episode ->
                goToStreamVideoScreen(episodeId = episode.itemId)
            }
        ))
    }

    private fun RecyclerView.addScrollObservable() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN).not()
                    && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.exploreAdditionalEpisodes()
                }
            }
        })
    }

    private fun setupObserver() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        uiState.collectLatest { newState ->
                            if (newState is GetAnimeDetails) newState.updateUi()
                        }
                    }
                }
            }

            verifyDetailsState()
        }
    }

    private fun goBackToPreviousScreen() = findNavController().popBackStack()

    private fun goToStreamVideoScreen(episodeId: String?) {
        episodeId?.let { id ->
            findNavController().navigate(
                directions = DetailsFragmentDirections.actionToAnimeVideoScreen(
                    argument = VideoStreamingArguments(
                        episodeId = id,
                        entryPointType = navArgs.argument.entryPointType
                    )
                )
            )
        } ?: error("episodeId not found")
    }

    private fun GetAnimeDetails.updateUi() {
        binding.apply {
            details = detailsData
            otherEpisodeItems?.let(otherEpisodeList::setItems)
        }
    }
}