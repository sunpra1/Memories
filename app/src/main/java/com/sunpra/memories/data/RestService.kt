package com.sunpra.memories.data

import com.sunpra.memories.data.json.LoginBody
import com.sunpra.memories.data.json.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestService {
    @POST("/user/login")
    suspend fun login(@Body() loginBody: LoginBody): Response<LoginResponse>
}