package com.sunpra.memories.data

import com.sunpra.memories.data.json.LoginBody
import com.sunpra.memories.data.json.LoginResponse
import com.sunpra.memories.data.json.Memory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestService {
    @POST("user/login")
    suspend fun login(@Body() loginBody: LoginBody): Response<LoginResponse>

    @GET("memory")
    suspend fun getMemories(@Header("Authorization") token: String?): Response<List<Memory>>
}