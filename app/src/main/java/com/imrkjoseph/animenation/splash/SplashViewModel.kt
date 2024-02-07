package com.imrkjoseph.animenation.splash

import com.imrkjoseph.animenation.splash.SplashFragmentDirections.Companion.actionToWelcomeScreen
import com.imrkjoseph.animenation.splash.SplashFragmentDirections.Companion.actionToDashboardScreen
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val currentUser: FirebaseUser?
) : ViewModel() {

    val navDirection = MutableLiveData<NavDirections>()

    init {
        checkScreenState()
    }

    private fun checkScreenState() {
        viewModelScope.launch {
            delay(2500)
            navDirection.value = (if (currentUser?.uid != null && currentUser.isEmailVerified) actionToDashboardScreen()
            else actionToWelcomeScreen())
        }
    }
}