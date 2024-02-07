package com.imrkjoseph.animenation.app.component

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import javax.inject.Singleton

@UnstableApi @Singleton
class CustomPlayerView(context: Context, attrs: AttributeSet?): PlayerView(context, attrs) {

    private var customPlayerInterface: CustomPlayerInterface? = null

    var exoPlayer: ExoPlayer? = null

    fun setupVideoPlayer(mediaUrl: String?) {
        mediaUrl?.let { url ->
            exoPlayer = ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()
            .apply {
                setMediaSource(getMediaSource(url = url))
                prepare()
                addListener(infoListener)
            }
        }
    }

    private fun getMediaSource(url: String): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        // Create a HLS media source pointing to a playlist uri.
        return if (url.contains("m3u8")) {
            HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(url))
        } else {
            // Create a Regular media source pointing to a playlist uri.
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
        }
    }

    private val infoListener = object: Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when(playbackState){
                Player.STATE_ENDED -> customPlayerInterface?.onMediaInfo(Player.STATE_ENDED)
                Player.STATE_READY -> customPlayerInterface?.onMediaInfo(Player.STATE_READY)
                Player.STATE_BUFFERING -> customPlayerInterface?.onMediaInfo(Player.STATE_BUFFERING)
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            customPlayerInterface?.onMediaInfo(Player.STATE_ENDED)
        }
    }

    fun playVideo(){
        player = exoPlayer
        exoPlayer?.playWhenReady = true
    }

    fun pauseVideo() {
        exoPlayer?.playWhenReady = false
    }

    fun releasePlayer(){
        player = null
        exoPlayer?.apply {
            playWhenReady = false
            release()
        }
    }

    fun resumePlayer(currentPosition: Long) {
        exoPlayer?.seekTo(currentPosition)
        exoPlayer?.playWhenReady = true
    }

    fun restartPlayer(){
        exoPlayer?.seekTo(0)
        exoPlayer?.playWhenReady = true
    }

    fun setPlayerInterface(playerInterface: CustomPlayerInterface) {
        customPlayerInterface = playerInterface
    }

    interface CustomPlayerInterface {
        fun onMediaInfo(mediaInfo: Int)
    }
}