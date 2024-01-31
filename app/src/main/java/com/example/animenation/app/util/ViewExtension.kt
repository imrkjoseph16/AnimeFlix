package com.example.animenation.app.util

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.VideoView
import androidx.viewpager2.widget.ViewPager2

fun View.setVisible(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.GONE
}

fun ViewPager2.autoScroll(milliSeconds: Long) {
    val handler = Handler()
    var scrollPosition = 0

    val runnable = object : Runnable {
        override fun run() {
            val count = adapter?.itemCount ?: 0
            setCurrentItem(scrollPosition++ % count, true)
            handler.postDelayed(this, milliSeconds)
        }
    }

    registerOnPageChangeCallback(
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                scrollPosition = position + 1
            }
        }
    )

    handler.post(runnable)
}

fun VideoView.infoListener(
    onMediaInfo: (MediaInfo) -> Unit
) {
    setOnPreparedListener { mediaPlayer ->
        mediaPlayer.setOnInfoListener { _, what, _ ->
            when (what) {
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> onMediaInfo.invoke(MediaInfo.RENDER_START)
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> onMediaInfo.invoke(MediaInfo.BUFFERING_START)
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> onMediaInfo.invoke(MediaInfo.BUFFERING_END)
            }
            true
        }

        scaleVideoView(mediaPlayer = mediaPlayer)
    }

    setOnErrorListener { _, _, _ ->
        onMediaInfo.invoke(MediaInfo.RENDER_FAILED)
        true
    }
}


private fun VideoView.scaleVideoView(mediaPlayer: MediaPlayer) {
    val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
    val screenRatio = width / height.toFloat()
    val scaleXSize = videoRatio / screenRatio
    scaleX = scaleXSize
}

enum class MediaInfo {
    RENDER_START,
    BUFFERING_START,
    BUFFERING_END,
    RENDER_FAILED
}

fun View.onTouchListener(
    context: Context,
    onDoubleTapClick: () -> Unit = { },
    onSingleTapClick: () -> Unit = { }
) {
    setOnTouchListener(object : OnTouchListener {
        private val gestureDetector =
            GestureDetector(context, object : SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    onDoubleTapClick.invoke()
                    return super.onDoubleTap(e)
                }

                override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                    onSingleTapClick.invoke()
                    return false
                }
            })

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(event)
            return true
        }
    })
}

enum class TapGesture {
    LEFT_TAP,
    RIGHT_TAP
}