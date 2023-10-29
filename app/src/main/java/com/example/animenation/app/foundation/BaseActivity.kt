package com.example.animenation.app.foundation

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : AppCompatActivity() {

    lateinit var binding: VB

    private var willHideStatusBar: Boolean = true

    protected open fun onActivityCreated() { initViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityCreated()
        setupActivity()
    }

    private fun setupActivity() {
        if (willHideStatusBar) setDecorationView((View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN))
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    private fun initViewBinding() {
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
    }

    fun setFullScreenMode() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setDecorationView(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun setDecorationView(decorView: Int) {
        window.decorView.systemUiVisibility = decorView
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // Hiding the keyboard when the user lose focus on the editText.
        if (currentFocus != null) hideSoftKeyboard()
        return super.dispatchTouchEvent(ev)
    }
}