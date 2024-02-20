package com.imrkjoseph.animenation.dashboard.shared.presentation.video.presentation

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.navigation.navArgs
import com.imrkjoseph.animenation.app.component.CustomPlayerView
import com.imrkjoseph.animenation.app.component.CustomRecyclerView
import com.imrkjoseph.animenation.app.component.ListItemPayloadDiffCallback
import com.imrkjoseph.animenation.app.foundation.BaseActivity
import com.imrkjoseph.animenation.app.shared.binder.component.CardDetailsSmallItem
import com.imrkjoseph.animenation.app.shared.binder.component.ShimmerLoadingItemBinder
import com.imrkjoseph.animenation.app.shared.binder.component.setupCardDetailsSmallItemBinder
import com.imrkjoseph.animenation.app.util.Default.Companion.DEFAULT_MOVIE
import com.imrkjoseph.animenation.app.util.formatString
import com.imrkjoseph.animenation.app.util.onTouchListener
import com.imrkjoseph.animenation.app.util.setEnabled
import com.imrkjoseph.animenation.app.util.setVisible
import com.imrkjoseph.animenation.dashboard.shared.domain.MoviesUseCase.VideoStreamRequest
import com.imrkjoseph.animenation.dashboard.shared.presentation.GetContentDetails
import com.imrkjoseph.animenation.dashboard.shared.presentation.SharedViewModel
import com.imrkjoseph.animenation.databinding.ActivityVideoStreamingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.imrkjoseph.animenation.R.id as appId
import com.imrkjoseph.animenation.R.string as appString

@UnstableApi @AndroidEntryPoint
class VideoStreaming : BaseActivity<ActivityVideoStreamingBinding>(bindingInflater = ActivityVideoStreamingBinding::inflate),
    CustomPlayerView.CustomPlayerInterface {

    private val viewModel: VideoStreamingViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by viewModels()

    private val args: VideoStreamingArgs by navArgs()

    override fun onActivityCreated() {
        super.onActivityCreated()
        with(binding) {
            configureViews()
            setupObserver()
            setDecorationView(decorView = flags)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) setDecorationView(decorView = flags)
    }

    private fun ActivityVideoStreamingBinding.configureViews() {
        with(videoView) {
            setPlayerInterface(this@VideoStreaming)
            getControlsView(appId.back).setOnClickListener { finish() }
            getControlsView(appId.video_episodes).setOnClickListener {
                setEpisodesTransition()
            }
            getControlsView(appId.previous_video).setOnClickListener {
                executePreviousNextVideo(isNext = false)
            }
            getControlsView(appId.next_video).setOnClickListener {
                executePreviousNextVideo(isNext = true)
            }

            getControlsView(appId.left_Screen).onTouchListener(
                context = this@VideoStreaming,
                onDoubleTapClick = { exoPlayer?.seekBack() },
                onSingleTapClick = { hideController() }
            )

            getControlsView(appId.right_Screen).onTouchListener(
                context = this@VideoStreaming,
                onDoubleTapClick = { exoPlayer?.seekForward() },
                onSingleTapClick = { hideController() }
            )
        }

        episodeList.setupBottomList()
    }

    private fun ActivityVideoStreamingBinding.setEpisodesTransition() {
        with(videoPlayerMotionLayout) {
            if (currentState == appId.firstScene) transitionToEnd()
            else transitionToStart()
        }
    }

    private fun CustomRecyclerView.setupBottomList() {
        recyclerViewAdapter.setDiffUtilCallBack(diffUtilCallback = ListItemPayloadDiffCallback())
        addItemBindings(viewHolders = ShimmerLoadingItemBinder)
        addItemBindings(viewHolders = setupCardDetailsSmallItemBinder(
            dtoRetriever = CardDetailsSmallItem::dto,
            onItemClick = {
                viewModel.currentEpisode = it.dto.itemPosition
                getFullVideoDetails(
                    VideoStreamingArguments(
                        detailsId = it.dto.detailsId,
                        typeOfMovie = it.dto.typeOfMovie,
                        episodeId = it.dto.itemId,
                        showId = it.dto.showId,
                        entryPointType = args.argument.entryPointType
                    )
                ).also { binding.setEpisodesTransition() }
            }
        ))
    }

    private fun getControlsView(viewId: Int) = findViewById<View>(viewId)

    @SuppressLint("SetTextI18n")
    private fun setupVideoDetails() {
        with(viewModel) {
            val title = getControlsView(appId.video_title) as TextView
            val previousVideoView = getControlsView(appId.previous_video)
            val nextVideoView = getControlsView(appId.next_video)

            with(binding) {
                videoDetails?.let {
                    nextVideoView.setEnabled(canEnabled = it.episodes.size + 1 != currentEpisode)
                    previousVideoView.setEnabled(canEnabled = currentEpisode != 1)

                    title.text = "${it.title} (${formatString(appString.title_episode, currentEpisode)})"
                } ?: {
                    previousVideoView.setVisible(canShow = false)
                    nextVideoView.setVisible(canShow = false)
                }
            }
        }
    }

    private fun executePreviousNextVideo(isNext: Boolean) {
        with(viewModel) {
            binding.videoDetails?.episodes?.let { list ->
                val episode = if (isNext) currentEpisode.inc()
                else currentEpisode.dec()
                currentEpisode = episode
                // We need to set the "minus(1)" in the list,
                // since arrayList starts with "0 index".
                getFullVideoDetails(
                    VideoStreamingArguments(
                        detailsId = args.argument.detailsId,
                        typeOfMovie = args.argument.typeOfMovie,
                        episodeId = list[episode.minus(1)].episodeId,
                        showId = list[episode.minus(1)].showId,
                        entryPointType = args.argument.entryPointType
                    )
                )
            }
        }

        with(binding.videoPlayerMotionLayout) {
            if (currentState == appId.secondScene) {
                transitionToStart()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            lifecycleScope.launch {
                launch {
                    uiItems.collectLatest {
                        it.updateUi()
                    }
                }

                launch {
                    sharedViewModel.sharedState.collectLatest {
                        if (it is GetContentDetails) {
                            binding.videoDetails = it.detailsData
                            getUiEpisodeItems(
                                details = it.detailsData
                            )
                        }
                    }
                }
            }

            streamLink.observe(this@VideoStreaming) {
                binding.videoView.apply {
                    if (exoPlayer != null) releasePlayer()
                    subtitleView?.setVisible(canShow = false)
                    setupVideoPlayer(
                        mediaUrl = it.sources?.get(0)?.url.toString(),
                        subtitleUrl = it.subtitles?.get(0)?.url,
                        customSubtitle = binding.customSubtitle,
                        withSubtitle = it.subtitles?.isNotEmpty() == true,
                    )
                }
            }
        }
    }

    private fun VideoStreamingUiItems.updateUi() {
        with(binding) {
            episodeItems?.let { bottomList = it }
            setupVideoDetails()
            executePendingBindings()
        }
    }

    private fun getFullVideoDetails(
        data: VideoStreamingArguments
    ) {
        viewModel.getStreamingState(
            data = VideoStreamRequest(
                detailsId = data.detailsId,
                episodeId = data.episodeId,
                showId = data.showId,
                episode = viewModel.currentEpisode,
                isStreamSeries = data.typeOfMovie != DEFAULT_MOVIE
            )
        )
        sharedViewModel.verifyDetailsState(
            detailsId = data.detailsId,
            entryPoint = data.entryPointType,
            typeOfMovie = data.typeOfMovie
        )
    }

    override fun onStart() {
        super.onStart()
        with(args.argument) {
            getFullVideoDetails(
                VideoStreamingArguments(
                    detailsId = detailsId,
                    typeOfMovie = typeOfMovie,
                    episodeId = episodeId,
                    showId = showId,
                    entryPointType = entryPointType
                )
            )
        }
    }

    override fun onPause() {
        binding.videoView.pauseVideo()
        super.onPause()
    }

    override fun onDestroy() {
        binding.videoView.releasePlayer()
        super.onDestroy()
    }

    override fun onMediaInfo(mediaInfo: Int) {
        binding.videoView.apply {
            when(mediaInfo){
                Player.STATE_ENDED -> executePreviousNextVideo(isNext = true)
                Player.STATE_READY -> playVideo()
            }
        }
    }
}

