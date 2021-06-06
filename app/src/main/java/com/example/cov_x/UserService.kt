package com.example.cov_x

import com.example.cov_x.models.LoginRequest
import com.example.cov_x.models.LoginResponse
import com.example.cov_x.models.RegisterRequest
import com.example.cov_x.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("auth/register")
     fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

     @POST("auth/login")
     fun loginUser(@Header("Authorization") authHeader: String, @Body loginRequest: LoginRequest): Call<LoginResponse>
}