package com.imrkjoseph.animenation.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrkjoseph.animenation.login.domain.LoginUserCredentials
import com.google.firebase.auth.FirebaseAuth
import com.imrkjoseph.animenation.app.util.coRunCatching
import com.imrkjoseph.animenation.register.data.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserCredentials: LoginUserCredentials,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(ShowLoginNoData)
    val loginState: LiveData<LoginState> get() = _loginState

    fun loginCredentials(userDetails: UserDetails) {
        viewModelScope.launch {
            _loginState.apply {
                value = ShowLoginLoading

                coRunCatching {
                    loginUserCredentials.invoke(userDetails)
                }.onSuccess {
                    value = ShowLoginSuccess(firebaseAuth.currentUser?.isEmailVerified)
                }.onFailure {
                    value = ShowLoginError(it)
                }

                value = ShowLoginDismissLoading
            }
        }
    }
}