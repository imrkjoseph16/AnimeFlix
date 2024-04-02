package com.imrkjoseph.animenation.main

import com.imrkjoseph.animenation.databinding.ActivityMainScreenHandlerBinding
import com.imrkjoseph.animenation.app.foundation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenHandler : BaseActivity<ActivityMainScreenHandlerBinding>(bindingInflater = ActivityMainScreenHandlerBinding::inflate)