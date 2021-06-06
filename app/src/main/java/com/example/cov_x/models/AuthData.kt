package com.example.cov_x.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class LoginRequest(
    var email: String = "",
    var password: String = ""
)

data class LoginResponse(
    var email: String = "",
    var id: Int = 0,
    var name: String = "",
    var token: String = ""
)

@Parcelize
data class RegisterRequest(
    var name: String = "",
    var email: String = "",
    var password: String = ""
): Parcelable

data class RegisterResponse(
    var message: String = ""
)
