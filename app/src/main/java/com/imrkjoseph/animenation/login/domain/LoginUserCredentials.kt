package com.imrkjoseph.animenation.login.domain

import com.imrkjoseph.animenation.login.data.repository.LoginRepository
import com.imrkjoseph.animenation.register.data.UserDetails
import javax.inject.Inject
class LoginUserCredentials @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(userDetails: UserDetails) = loginRepository.loginCredentials(userDetails)
}