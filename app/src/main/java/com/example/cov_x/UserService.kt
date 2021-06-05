package com.example.cov_x

import com.example.cov_x.models.RegisterRequest
import com.example.cov_x.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("auth/register")
     fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}