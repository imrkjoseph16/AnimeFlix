package com.imrkjoseph.animenation.app.util

import android.app.Activity
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shashank.sony.fancytoastlib.FancyToast


fun View.setVisible(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.GONE
}

fun View.setInVisibility(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.INVISIBLE
}

fun View.setEnabled(canEnabled: Boolean) {
    if (canEnabled) this.alpha = 1.0F else this.alpha = 0.4F
    this.isEnabled  = canEnabled
}

fun Activity.showFancyToast(
    message: String,
    style: Int = FancyToast.SUCCESS,
    duration: Int = FancyToast.LENGTH_SHORT
) = FancyToast.makeText(this, message, duration, style,false).show()

fun Fragment.showFancyToast(
    message: String,
    style: Int = FancyToast.SUCCESS,
    duration: Int = FancyToast.LENGTH_SHORT
) = this.context?.let { FancyToast.makeText(it, message, duration, style,false).show() }

fun Activity.formatString(stringResId: Int, vararg args: Any?): String =
    String.format(getString(stringResId, *args))

fun Fragment.findNavControllerById(@IdRes id: Int): NavController {
    var parent = parentFragment
    while (parent != null) {
        if (parent is NavHostFragment && parent.id == id) {
            return parent.navController
        }
        parent = parent.parentFragment
    }
    throw RuntimeException("NavController with specified id not found")
}

fun View.onTouchListener(
    context: Context,
    onDoubleTapClick: () -> Unit = { },
    onSingleTapClick: () -> Unit = { }
) {
    setOnTouchListener(object : View.OnTouchListener {
        private val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
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