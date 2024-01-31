package com.example.animenation.app.component

import android.content.Context
import android.support.annotation.AttrRes
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.animenation.app.util.setVisible
import com.example.animenation.databinding.WidgetSearchEditTextBinding
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rx.Subscription
import java.util.concurrent.TimeUnit

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var searchListener: SearchViewListener? = null

    private var searchJob: Subscription? = null

    var onResultFinished: () -> Unit = {}

    init {
        WidgetSearchEditTextBinding.inflate(LayoutInflater.from(context), this, false).apply {
            configureViews()
            setupObserver()
            addView(this.root)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun WidgetSearchEditTextBinding.configureViews() {
        searchView.addTextChangedListener {
            GlobalScope.launch(Dispatchers.Main) {
                if (it.toString().isNotEmpty()) runSearchObserver()
                else clearFields()
                userTyping(text = it)
            }
        }

        searchView.setOnEditorActionListener { text, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) searchListener?.onSearchTap(text.toString())
            false
        }

        clearSearch.setOnClickListener {
            clearFields()
        }
    }

    private fun WidgetSearchEditTextBinding.runSearchObserver() {
        searchJob = RxTextView.textChanges(searchView)
            .debounce(2, TimeUnit.SECONDS)
            .subscribe { newText: CharSequence? ->
                searchListener?.onUserTyped(newText = newText.toString())
            }
    }

    private fun WidgetSearchEditTextBinding.setupObserver() {
        onResultFinished = {
            searchLoading.setLoadingVisible(canShow = false)
        }
    }

    private fun WidgetSearchEditTextBinding.userTyping(text: Editable?) {
        val visible = text.toString().isEmpty().not()
        clearSearch.setVisible(canShow = visible)
        searchLoading.setLoadingVisible(canShow = visible)
    }

    private fun ProgressBar.setLoadingVisible(canShow: Boolean) {
        isIndeterminate = canShow
        setVisible(canShow = canShow)
    }

    private fun WidgetSearchEditTextBinding.clearFields() {
        searchView.text.clear()
        searchJob?.unsubscribe()
        searchListener?.onClearSearch()
    }

    fun setViewListener(listener: SearchViewListener) {
        searchListener = listener
    }

    interface SearchViewListener {
        fun onUserTyped(newText: String)
        fun onSearchTap(searchKey: String)
        fun onClearSearch()
    }
}