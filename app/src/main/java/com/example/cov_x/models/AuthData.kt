package com.example.cov_x.models

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)

data class RegisterRequest(
    var name: String = "",
    var email: String = "",
    var password: String = ""
)

data class RegisterResponse(
    var message: String = ""
)
