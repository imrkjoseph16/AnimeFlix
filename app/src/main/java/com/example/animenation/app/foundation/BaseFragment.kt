package com.example.animenation.app.foundation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.animenation.main.MainScreenHandler

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    lateinit var binding: VB

    protected open fun onCreated(savedInstanceState: Bundle?) = Unit

    protected open fun onViewCreated() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = initViewBinding(inflater)

    private fun initViewBinding(
        inflater: LayoutInflater
    ): View {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    fun getAppActivity(): AppCompatActivity = (activity as AppCompatActivity)

    fun getMainActivity(): MainScreenHandler = (activity as MainScreenHandler)

    fun onBackPressedCallBack(onBackClicked: () -> Unit) =
    (activity as AppCompatActivity).onBackPressedDispatcher.addCallback(viewLifecycleOwner,
    onBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = onBackClicked.invoke()
    })
}