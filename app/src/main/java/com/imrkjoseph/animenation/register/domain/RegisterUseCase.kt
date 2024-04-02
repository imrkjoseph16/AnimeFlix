package com.imrkjoseph.animenation.register.domain

import com.imrkjoseph.animenation.register.data.repository.RegisterRepository
import com.imrkjoseph.animenation.register.data.UserDetails
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend fun registerCredentials(userDetails: UserDetails) = registerRepository.registerCredentials(userDetails)

    suspend fun sendEmailVerification() = registerRepository.sendEmailVerification()

    suspend fun saveFireStoreDetails(details: HashMap<String, String?>) = registerRepository.saveFireStoreDetails(details)
}