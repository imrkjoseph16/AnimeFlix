package com.imrkjoseph.animenation.register.data.dto

import com.imrkjoseph.animenation.register.domain.data.ICreateUserCredential
import com.imrkjoseph.animenation.register.domain.data.ISaveDetailsFireStore
import com.imrkjoseph.animenation.register.domain.data.ISendEmailVerification
import com.google.firebase.auth.AuthResult

data class RegisterCredentialResponse(val auth: AuthResult) : ICreateUserCredential {
    override val authResult: AuthResult
        get() = auth
}

data class SendEmailVerificationResponse(val isVerified: Boolean) : ISendEmailVerification {
    override val isEmailVerified: Boolean
        get() = isVerified
}

data class SaveDetailsFireStore(val isSavedSuccess: Boolean) : ISaveDetailsFireStore {
    override val isSaveSuccess: Boolean
        get() = isSavedSuccess
}