package com.sunpra.memories.data

import com.sunpra.memories.data.json.LoginBody
import com.sunpra.memories.data.json.LoginResponse
import com.sunpra.memories.data.json.Memory
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RestService {
    @POST("user/login")
    suspend fun login(@Body() loginBody: LoginBody): Response<LoginResponse>

    @GET("memory")
    suspend fun getMemories(@Header("Authorization") token: String?): Response<List<Memory>>

    @POST("memory")
    @Multipart
    suspend fun addMemory(
        @Header("Authorization") token: String?,
        @Part() body: List<MultipartBody.Part>
    ): Response<Memory>
}