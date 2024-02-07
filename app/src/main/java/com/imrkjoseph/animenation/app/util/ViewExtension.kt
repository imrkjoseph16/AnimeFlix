package com.imrkjoseph.animenation.app.util

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shashank.sony.fancytoastlib.FancyToast

fun View.setVisible(canShow: Boolean) {
    this.visibility = if (canShow) View.VISIBLE else View.GONE
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

enum class TapGesture {
    LEFT_TAP,
    RIGHT_TAP
}